package com.mm.coreinfraqdsl.repository;

import java.util.List;

import com.mm.coredomain.domain.Item;

public interface AdminCustomRepository {
	List<Item> getItemsByPage(Integer page);

	Long getPageNum();
}
