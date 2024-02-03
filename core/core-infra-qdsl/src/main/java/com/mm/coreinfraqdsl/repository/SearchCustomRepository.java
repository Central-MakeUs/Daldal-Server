package com.mm.coreinfraqdsl.repository;

import java.util.List;

import com.mm.coredomain.domain.Item;

public interface SearchCustomRepository {
	List<Item> searchItems(Integer page, String keyword);
}
