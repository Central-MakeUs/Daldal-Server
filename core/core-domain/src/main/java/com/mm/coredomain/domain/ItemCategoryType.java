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
		"티셔츠",
		"니트/스웨터",
		"셔츠",
		"블라우스",
		"맨투맨/스웨트셔츠",
		"후드",
		"민소매/슬리브리스",
		"카디건",
		"재킷",
		"레더재킷",
		"트위드재킷",
		"코트",
		"숏패딩",
		"롱패딩",
		"경량 패딩",
		"트렌치코트",
		"사파리/헌팅재킷",
		"점퍼",
		"무스탕",
		"베스트",
		"레인코트",
		"미니원피스",
		"미디원피스",
		"롱원피스",
		"라운드 니트",
		"브이넥 니트",
		"터틀넥 니트",
		"오프숄더 니트",
		"스퀘어넥 니트",
		"니트 베스트",
		"니트원피스",
		"카디건",
		"트레이닝 상의",
		"트레이닝 세트",
		"시밀러룩",
		"스커트 세트",
		"팬츠 세트",
		"원피스 세트",
		"임부복 상의",
		"임부복 아우터",
		"임부복 원피스"
	);
	private static final List<String> BottomsList = List.of(
		"일자팬츠",
		"슬랙스팬츠",
		"숏팬츠",
		"와이드팬츠",
		"스키니팬츠",
		"부츠컷팬츠",
		"조거팬츠",
		"점프수트",
		"레깅스",
		"기타팬츠",
		"미니스커트",
		"미디스커트",
		"롱스커트",
		"트레이닝 하의",
		"임부복 스커트",
		"임부복 바지"
	);

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
