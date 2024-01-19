package com.mm.api.Item.controller;

import com.mm.api.Item.dto.request.ItemCreateRequest;
import com.mm.api.Item.dto.request.ItemUpdateRequest;
import com.mm.api.Item.dto.response.ItemDetailResponse;
import com.mm.api.Item.dto.response.ItemResponse;
import com.mm.api.Item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping("/items")
    public ResponseEntity<?> createItem(@RequestBody ItemCreateRequest request){
        ItemResponse response = itemService.createItem(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<?> updateItem(@RequestParam Long id, @RequestBody ItemUpdateRequest request){
        ItemResponse response = itemService.updateItem(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<?> updateItem(@RequestParam Long id){
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    // 관리자 권한

    @GetMapping("/items")
    public ResponseEntity<?> getItems(@RequestParam(required = false, defaultValue = "1") Integer page){
        List<ItemResponse> responses = itemService.getItems(page);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<?> getItemDetail(@RequestParam Long id){
        ItemDetailResponse response = itemService.getItemDetail(id);
        return ResponseEntity.ok(response);
    }
}
