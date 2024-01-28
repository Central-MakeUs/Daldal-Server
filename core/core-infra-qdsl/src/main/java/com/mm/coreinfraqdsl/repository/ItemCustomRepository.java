package com.mm.coreinfraqdsl.repository;

import java.util.List;

import com.mm.coredomain.domain.Item;
import com.mm.coredomain.domain.ItemCategoryType;

public interface ItemCustomRepository {
	List<Item> getItemsByPage(Integer integer, ItemCategoryType categoryType);
}
