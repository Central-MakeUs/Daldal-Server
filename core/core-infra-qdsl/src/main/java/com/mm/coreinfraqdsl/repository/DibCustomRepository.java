package com.mm.coreinfraqdsl.repository;

import java.util.List;

import com.mm.coredomain.domain.Dib;
import com.mm.coredomain.domain.Member;

public interface DibCustomRepository {
	List<Dib> getDibsByPage(Integer page, Member member);

	Long getPageNum(Member member);
}
