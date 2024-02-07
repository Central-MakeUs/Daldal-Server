package com.mm.api.domain.admin.service;

import com.mm.api.domain.admin.dto.response.AdminItemListResponse;
import com.mm.api.domain.item.dto.response.ItemDetailResponse;
import com.mm.api.exception.CustomException;
import com.mm.coredomain.domain.Item;
import com.mm.coredomain.domain.ItemImage;
import com.mm.coredomain.domain.ItemVideo;
import com.mm.coredomain.repository.ItemRepository;
import com.mm.coreinfrafeign.crawler.service.CrawlerService;
import com.mm.coreinfraqdsl.repository.AdminCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static com.mm.api.exception.ErrorCode.ITEM_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {
    private final ItemRepository itemRepository;
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

        Long pageNum = adminCustomRepository.getPageNum();

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
