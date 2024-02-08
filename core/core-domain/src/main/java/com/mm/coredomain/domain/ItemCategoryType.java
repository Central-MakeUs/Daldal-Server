package com.mm.coredomain.domain;

import java.util.List;

import lombok.Getter;

@Getter
public enum ItemCategoryType {
	TOPS("상의", 3),
	BOTTOMS("하의", 3),
	FASHION("패션잡화", 3),
	BEAUTY("뷰티", 3),
	ETC("기타", 3);

	private final String value;
	private final Integer refundPercent;
	private static final List<String> TopsList = List.of(
		"상의",
		"아우터",
		"원피스",
		"니트/카디건",
		"투피스/세트");
	private static final List<String> BottomsList = List.of(
		"바지",
		"스커트");

	ItemCategoryType(String value, Integer refundPercent) {
		this.value = value;
		this.refundPercent = refundPercent;
	}

	public static ItemCategoryType fromValueForClothes(String input) {
		if (TopsList.contains(input)) {
			return ItemCategoryType.TOPS;
		}
		if (BottomsList.contains(input)) {
			return ItemCategoryType.BOTTOMS;
		}
		return ItemCategoryType.FASHION;
	}

	public static ItemCategoryType fromValue(String input) {
		for (ItemCategoryType categoryType : ItemCategoryType.values()) {
			if (categoryType.value.equals(input)) {
				return categoryType;
			}
		}
		return ItemCategoryType.ETC;
	}

	public static ItemCategoryType of(String input) {
		try {
			return ItemCategoryType.valueOf(input.toUpperCase());
		} catch (Exception e) {
			throw new IllegalArgumentException("존재하지 않는 아이템 카테고리 타입입니다. : " + input);
		}
	}
}
