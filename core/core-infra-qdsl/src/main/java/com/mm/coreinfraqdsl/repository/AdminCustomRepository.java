package com.mm.coreinfraqdsl.repository;

import java.util.List;

import com.mm.coredomain.domain.Buy;
import com.mm.coredomain.domain.Item;
import com.mm.coredomain.domain.Member;

public interface AdminCustomRepository {
	List<Item> getItemsByPage(Integer page);

	Long getItemsPageNum();

	List<Buy> getBuysAdminByPage(Integer page);

	Long getBuysAdminPageNum();

	List<Buy> getWithdrawsAdminByPage(Integer page);

	Long getWithdrawsAdminPageNum();

	List<Buy> getWithdrawsAdminByPageByMember(Integer page, Member member);

	Long getWithdrawsAdminPageNumByMember(Member member);
}
