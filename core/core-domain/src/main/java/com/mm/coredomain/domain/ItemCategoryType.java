package com.mm.coredomain.domain;

import lombok.Getter;

@Getter
public enum ItemCategoryType {
	CLOTHES("의류", 3),
	BAG("가방", 3),
	SHOES("슈즈", 3),
	FASHION("패션잡화", 3),
	JEWELRY("주얼리", 3),
	BEAUTY("뷰티", 3),
	FOOD("푸드", 3),
	LIFE("라이프", 3);

	private final String value;
	private final Integer refundPercent;

	ItemCategoryType(String value, Integer refundPercent) {
		this.value = value;
		this.refundPercent = refundPercent;
	}

	public static ItemCategoryType fromValue(String input) {
		for (ItemCategoryType categoryType : ItemCategoryType.values()) {
			if (categoryType.value.equals(input)) {
				return categoryType;
			}
		}
		return ItemCategoryType.FASHION;
	}

	public static ItemCategoryType of(String input) {
		try {
			return ItemCategoryType.valueOf(input.toUpperCase());
		} catch (Exception e) {
			throw new IllegalArgumentException("존재하지 않는 아이템 카테고리 타입입니다. : " + input);
		}
	}
}
