package com.mm.api.domain.item.service;

import com.mm.api.domain.dib.service.DibService;
import com.mm.api.domain.item.dto.request.ItemCreateRequest;
import com.mm.api.domain.item.dto.request.ItemUpdateRequest;
import com.mm.api.domain.item.dto.response.ItemDetailResponse;
import com.mm.api.domain.item.dto.response.ItemResponse;
import com.mm.api.exception.CustomException;
import com.mm.coredomain.domain.*;
import com.mm.coredomain.repository.ItemRepository;
import com.mm.coreinfraqdsl.repository.ItemCustomRepository;
import com.mm.coresecurity.oauth.OAuth2UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static com.mm.api.exception.ErrorCode.ITEM_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemCustomRepository itemCustomRepository;
    private final DibService dibService;

    public ItemResponse createItem(ItemCreateRequest request) {
        Item item = request.toEntity();

        List<ItemImage> itemImages = getItemImages(request.imageUrls(), item);
        List<ItemVideo> itemVideos = getItemVideos(request.videoUrls(), item);

        item.setItemImages(itemImages);
        item.setItemVideos(itemVideos);

        Item savedItem = itemRepository.save(item);
        return ItemResponse.of(savedItem, false);
    }

    @Transactional(readOnly = true)
    public List<ItemResponse> getItems(Integer page, OAuth2UserDetails userDetails, String itemCategoryType) {
        List<Item> items = itemCustomRepository.getItemsByPage(page, itemCategoryType);

        List<Boolean> dibs = dibService.getDib(items, userDetails);

        return IntStream.range(0, items.size())
                .mapToObj(i ->
                        ItemResponse.of(items.get(i), dibs.get(i)))
                .toList();
    }

    @Transactional(readOnly = true)
    public ItemDetailResponse getItemDetail(Long id, OAuth2UserDetails userDetails) {
        Item item = getItem(id);

        List<String> images = item.getItemImages().stream()
                .map(ItemImage::getUrl)
                .toList();
        List<String> videos = item.getItemVideos().stream()
                .map(ItemVideo::getUrl)
                .toList();

        List<Boolean> dibs = dibService.getDib(List.of(item), userDetails);

        return ItemDetailResponse.of(item, images, videos, dibs.get(0));
    }

    public ItemResponse updateItem(Long id, ItemUpdateRequest request) {
        Item item = getItem(id);

        ItemUpdate itemUpdate = getItemUpdate(request);
        item.updateItem(itemUpdate);

        List<ItemImage> itemImages = getItemImages(request.imageUrls(), item);
        List<ItemVideo> itemVideos = getItemVideos(request.videoUrls(), item);

        item.setItemImages(itemImages);
        item.setItemVideos(itemVideos);

        return ItemResponse.of(item, false);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    private Item getItem(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new CustomException(ITEM_NOT_FOUND));
    }

    private ItemUpdate getItemUpdate(ItemUpdateRequest request) {
        return ItemUpdate.builder()
                .detail(request.detail())
                .redirectUrl(request.redirectUrl())
                .categoryType(ItemCategoryType.of(request.categoryType()))
                .price(request.price())
                .refund(request.refund())
                .rating(request.rating())
                .thumbnailUrl(request.thumbnailUrl())
                .build();
    }

    private List<ItemVideo> getItemVideos(List<String> videoUrls, Item item) {
        return videoUrls
                .stream()
                .map(url ->
                        ItemVideo.builder()
                                .url(url)
                                .item(item)
                                .build())
                .toList();
    }

    private List<ItemImage> getItemImages(List<String> imageUrls, Item item) {
        return imageUrls
                .stream()
                .map(url ->
                        ItemImage.builder()
                                .url(url)
                                .item(item)
                                .build())
                .toList();
    }
}
