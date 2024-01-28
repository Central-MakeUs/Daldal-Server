package com.mm.coreinfraqdsl.repository;

import static com.mm.coredomain.domain.QDib.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mm.coredomain.domain.Dib;
import com.mm.coredomain.domain.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DibCustomRepositoryImpl implements DibCustomRepository {
	private final JPAQueryFactory jpaQueryFactory;

	private static final Long PAGE_OFFSET = 10L;

	@Override
	public List<Dib> getDibsByPage(Integer page, Member member) {
		return jpaQueryFactory.selectFrom(dib)
			.where(dib.member.eq(member))
			.offset((page - 1) * PAGE_OFFSET)
			.limit(PAGE_OFFSET)
			.fetch();
	}
}
