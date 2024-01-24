package com.mm.coreinfraqdsl.repository;

import static com.mm.coredomain.domain.QBuy.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mm.coredomain.domain.Buy;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BuyCustomRepositoryImpl implements BuyCustomRepository {
	private final JPAQueryFactory jpaQueryFactory;

	private static final Long PAGE_OFFSET = 10L;

	@Override
	public List<Buy> getBuysByPage(Integer page) {
		return jpaQueryFactory.selectFrom(buy)
			.offset((page - 1) * PAGE_OFFSET)
			.limit(PAGE_OFFSET)
			.fetch();
	}
}
