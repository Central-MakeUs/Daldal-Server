package com.mm.api.Item.service;

import com.mm.api.Item.dto.request.ItemCreateRequest;
import com.mm.api.Item.dto.request.ItemUpdateRequest;
import com.mm.api.Item.dto.response.ItemDetailResponse;
import com.mm.api.Item.dto.response.ItemResponse;
import com.mm.api.exception.CustomException;
import com.mm.api.exception.ErrorCode;
import com.mm.coredomain.domain.*;
import com.mm.coredomain.repository.ItemRepository;
import com.mm.coreinfraqdsl.repository.ItemCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mm.api.exception.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemCustomRepository itemCustomRepository;

    public ItemResponse createItem(ItemCreateRequest request) {
        Item item = request.toEntity();

        List<ItemImage> itemImages = request.ImageUrls()
                .stream()
                .map(url ->
                        ItemImage.builder()
                                .url(url)
                                .item(item)
                                .build())
                .toList();

        List<ItemVideo> itemVideos = request.ImageUrls()
                .stream()
                .map(url ->
                        ItemVideo.builder()
                                .url(url)
                                .item(item)
                                .build())
                .toList();

        item.setItemImages(itemImages);
        item.setItemVideos(itemVideos);

        Item savedItem = itemRepository.save(item);
        return ItemResponse.of(savedItem);
    }

    public List<ItemResponse> getItems(Integer page) {
        List<Item> items = itemCustomRepository.getItemsByPage(page);

        return items.stream()
                .map(ItemResponse::of)
                .toList();
    }

    public ItemDetailResponse getItemDetail(Long id){
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new CustomException(ITEM_NOT_FOUND));

        List<String> images = item.getItemImages().stream()
                .map(ItemImage::getUrl)
                .toList();
        List<String> videos = item.getItemVideos().stream()
                .map(ItemVideo::getUrl)
                .toList();

        return ItemDetailResponse.of(item, images, videos);
    }

    public ItemResponse updateItem(Long id, ItemUpdateRequest request) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new CustomException(ITEM_NOT_FOUND));

        ItemUpdate itemUpdate = ItemUpdate.builder()
                .detail(request.detail())
                .redirectUrl(request.redirectUrl())
                .categoryType(ItemCategoryType.of(request.categoryType()))
                .price(request.price())
                .refund(request.refund())
                .rating(request.rating())
                .thumbnailUrl(request.thumbnailUrl())
                .build();
        item.updateItem(itemUpdate);

        List<ItemImage> itemImages = request.ImageUrls()
                .stream()
                .map(url ->
                        ItemImage.builder()
                                .url(url)
                                .item(item)
                                .build())
                .toList();
        List<ItemVideo> itemVideos = request.ImageUrls()
                .stream()
                .map(url ->
                        ItemVideo.builder()
                                .url(url)
                                .item(item)
                                .build())
                .toList();

        item.setItemImages(itemImages);
        item.setItemVideos(itemVideos);

        return ItemResponse.of(item);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
