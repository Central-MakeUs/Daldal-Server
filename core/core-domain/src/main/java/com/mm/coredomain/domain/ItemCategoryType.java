package com.mm.coredomain.domain;

public enum ItemCategoryType {
    COSMETIC,
    CLOTHES;

    public static ItemCategoryType of(String input) {
        try{
            return ItemCategoryType.valueOf(input.toUpperCase());
        } catch (Exception e){
            throw new IllegalArgumentException("존재하지 않는 아이템 카테고리 타입입니다. : " + input);
        }
    }
}
