package com.mm.coreinfraqdsl.repository;

import java.util.List;

import com.mm.coredomain.domain.Buy;
import com.mm.coredomain.domain.Member;

public interface BuyCustomRepository {
	List<Buy> getBuysByPage(Integer page);

	List<Buy> getBuysMeByMember(Integer page, Member member);

	Long getPageNum();

	Long getBuysMePageNum(Member member);
}
