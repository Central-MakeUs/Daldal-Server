package com.mm.coreinfraqdsl.repository;

import java.util.List;

import com.mm.coredomain.domain.Buy;

public interface BuyCustomRepository {
	List<Buy> getBuysByPage(Integer page);
}
