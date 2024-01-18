package com.mm.api.Item.service;

import com.mm.api.Item.dto.request.ItemCreateRequest;
import com.mm.api.Item.dto.request.ItemUpdateRequest;
import com.mm.api.Item.dto.response.ItemResponse;
import com.mm.api.exception.CustomException;
import com.mm.api.exception.ErrorCode;
import com.mm.coredomain.domain.Item;
import com.mm.coredomain.domain.ItemCategoryType;
import com.mm.coredomain.domain.ItemUpdate;
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

        Item savedItem = itemRepository.save(item);
        return ItemResponse.of(savedItem);
    }

    public List<ItemResponse> getItems(Integer page) {
        List<Item> items = itemCustomRepository.getItemsByPage(page);

        return items.stream()
                .map(ItemResponse::of)
                .toList();
    }

    // TODO 이미지, 비디오 url?
    public void getItemDetail(){}

    public ItemResponse updateItem(Long id, ItemUpdateRequest request) {
        ItemUpdate itemUpdate = ItemUpdate.builder()
                .detail(request.detail())
                .redirectUrl(request.redirectUrl())
                .categoryType(ItemCategoryType.of(request.categoryType()))
                .price(request.price())
                .refund(request.refund())
                .rating(request.rating())
                .thumbnailUrl(request.thumbnailUrl())
                .build();

        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new CustomException(ITEM_NOT_FOUND));

        item.updateItem(itemUpdate);
        return ItemResponse.of(item);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
