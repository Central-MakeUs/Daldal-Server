package com.mm.coreinfraqdsl.repository;

import java.util.List;

import com.mm.coredomain.domain.Item;

public interface ItemCustomRepository {
	List<Item> getItemsByPage(Integer page, String categoryType);

	Long getPageNum(String category);
}
