package com.mm.api.domain.admin.service;

import com.mm.api.domain.admin.dto.request.RejectBuyRefundStatusRequest;
import com.mm.api.domain.admin.dto.response.AdminItemListResponse;
import com.mm.api.domain.admin.dto.response.BuyListResponse;
import com.mm.api.domain.admin.dto.response.WithdrawListResponse;
import com.mm.api.domain.admin.dto.response.WithdrawResponse;
import com.mm.api.domain.buy.dto.response.BuyResponse;
import com.mm.api.domain.item.dto.response.ItemDetailResponse;
import com.mm.api.exception.CustomException;
import com.mm.api.exception.ErrorCode;
import com.mm.coredomain.domain.*;
import com.mm.coredomain.repository.BuyRepository;
import com.mm.coredomain.repository.ItemRepository;
import com.mm.coredomain.repository.MemberRepository;
import com.mm.coreinfrafeign.crawler.service.CrawlerService;
import com.mm.coreinfraqdsl.repository.AdminCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static com.mm.api.exception.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {
    private final ItemRepository itemRepository;
    private final BuyRepository buyRepository;
    private final MemberRepository memberRepository;
    private final CrawlerService crawlerService;
    private final AdminCustomRepository adminCustomRepository;

    public void updateVideoUrl(Long itemId, String url) {
        Item item = getItem(itemId);
        ItemVideo itemVideo = ItemVideo.builder()
                .url(url)
                .item(item)
                .build();
        item.setItemVideos(List.of(itemVideo));
    }

    public ItemDetailResponse crawlItem(String url) {
        Item item = crawlerService.getZigZagItemByCrawler(url);
        Item savedItem = itemRepository.save(item);
        return getItemDetailResponseByItem(savedItem);
    }

    @Transactional(readOnly = true)
    public AdminItemListResponse getItems(Integer page) {
        List<Item> items = adminCustomRepository.getItemsByPage(page);

        List<List<String>> itemImageLists = items.stream()
                .map(item ->
                        item.getItemImages()
                                .stream()
                                .map(ItemImage::getUrl)
                                .toList())
                .toList();

        List<List<String>> itemVideoLists = items.stream()
                .map(item ->
                        item.getItemVideos()
                                .stream()
                                .map(ItemVideo::getUrl)
                                .toList())
                .toList();

        List<AdminItemListResponse.AdminItemResponse> adminItemResponses = IntStream.range(0, items.size())
                .mapToObj(i ->
                        AdminItemListResponse.AdminItemResponse.of(items.get(i), itemImageLists.get(i), itemVideoLists.get(i)))
                .toList();

        Long pageNum = adminCustomRepository.getItemsPageNum();

        return new AdminItemListResponse(pageNum, adminItemResponses);
    }

    public void updateItemSuggest(Long itemId) {
        Item item = getItem(itemId);
        item.setItemSuggested();
    }

    public void updateItemNotSuggest(Long itemId) {
        Item item = getItem(itemId);
        item.setItemNotSuggested();
    }

    @Transactional(readOnly = true)
    public WithdrawListResponse getWithdraws(Integer page) {
        List<Buy> buys = adminCustomRepository.getWithdrawsAdminByPage(page);
        List<WithdrawResponse> withdrawResponses = buys.stream()
                .map(buy -> {
                    Member member = buy.getMember();
                    return WithdrawResponse.of(buy, member);
                })
                .toList();
        Long pageNum = adminCustomRepository.getWithdrawsAdminPageNum();

        return new WithdrawListResponse(pageNum, withdrawResponses);
    }

    @Transactional(readOnly = true)
    public WithdrawListResponse getWithdrawsByMember(Integer page, Long memberId) {
        Member member = getMember(memberId);
        List<Buy> buys = adminCustomRepository.getWithdrawsAdminByPageByMember(page, member);
        List<WithdrawResponse> withdrawResponses = buys.stream()
                .map(buy -> {
                    return WithdrawResponse.of(buy, member);
                })
                .toList();
        Long pageNum = adminCustomRepository.getWithdrawsAdminPageNumByMember(member);

        return new WithdrawListResponse(pageNum, withdrawResponses);
    }

    public BuyResponse approvePointsWithdraw(Long buyId) {
        Buy buy = getBuy(buyId);
        isBuyHaveRefund(buy);

        buy.approveWithdrawnStatus();
        buy.setApprovedTimeNow();

        Member member = buy.getMember();
        buy.updatePointsBeforeRefund(member.getPoint());
        member.minusMemberPoint(buy.getRefund());
        buy.updatePointsAfterRefund(member.getPoint());
        return BuyResponse.of(buy, member);
    }

    public BuyResponse rejectPointsWithdraw(Long buyId, RejectBuyRefundStatusRequest request) {
        Buy buy = getBuy(buyId);
        buy.rejectWithdrawnStatus(request.rejectReason());
        buy.setApprovedTimeNow();

        return BuyResponse.of(buy, buy.getMember());
    }

    @Transactional(readOnly = true)
    public BuyListResponse getBuys(Integer page) {
        List<Buy> buys = adminCustomRepository.getBuysAdminByPage(page);
        List<BuyResponse> buyResponses = buys.stream()
                .map(buy -> BuyResponse.of(buy, buy.getMember()))
                .toList();

        Long pageNum = adminCustomRepository.getBuysAdminPageNum();
        return new BuyListResponse(pageNum, buyResponses);
    }

    public BuyResponse approveBuyRefundStatus(Long buyId) {
        Buy buy = getBuy(buyId);
        buy.approveRefundStatus();
        buy.setApprovedTimeNow();

        Member member = buy.getMember();
        buy.updatePointsBeforeRefund(member.getPoint());
        member.plusMemberPoint(buy.getRefund());
        buy.updatePointsAfterRefund(member.getPoint());
        return BuyResponse.of(buy, buy.getMember());
    }

    public BuyResponse rejectBuyRefundStatus(Long buyId, RejectBuyRefundStatusRequest request) {
        Buy buy = getBuy(buyId);
        buy.rejectRefundStatus(request.rejectReason());
        buy.setApprovedTimeNow();

        return BuyResponse.of(buy, buy.getMember());
    }

    private static void isBuyHaveRefund(Buy buy) {
        if (buy.getRefund() == null || buy.getPurchase() == null) {
            throw new CustomException(BUYS_NOT_HAVE_REFUND);
        }
    }

    private Buy getBuy(Long id) {
        return buyRepository.findById(id)
                .orElseThrow(() -> new CustomException(BUY_NOT_FOUND));
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private Item getItem(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new CustomException(ITEM_NOT_FOUND));
    }

    private ItemDetailResponse getItemDetailResponseByItem(Item savedItem) {
        List<String> images = null;
        if (savedItem.getItemImages() != null) {
            images = savedItem.getItemImages().stream()
                    .map(ItemImage::getUrl)
                    .toList();
        }
        List<String> videos = null;
        if (savedItem.getItemVideos() != null) {
            videos = savedItem.getItemVideos().stream()
                    .map(ItemVideo::getUrl)
                    .toList();
        }
        return ItemDetailResponse.of(savedItem, images, videos, false);
    }
}
