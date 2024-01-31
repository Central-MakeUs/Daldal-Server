package com.mm.coreinfraqdsl.repository;

import com.mm.coredomain.domain.Item;

import java.util.List;

public interface ItemCustomRepository {
    List<Item> getItemsByPage(Integer page, String categoryType);
}
