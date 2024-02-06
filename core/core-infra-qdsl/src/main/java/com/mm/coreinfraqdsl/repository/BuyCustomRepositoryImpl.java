package com.mm.coreinfraqdsl.repository;

import static com.mm.coredomain.domain.QBuy.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mm.coredomain.domain.Buy;
import com.mm.coredomain.domain.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BuyCustomRepositoryImpl implements BuyCustomRepository {
	private final JPAQueryFactory jpaQueryFactory;

	private static final Long PAGE_OFFSET = 10L;
	private static final Long PAGE_OFFSET_ME = 9L;

	@Override
	public List<Buy> getBuysByPage(Integer page) {
		return jpaQueryFactory.selectFrom(buy)
			.offset((page - 1) * PAGE_OFFSET)
			.limit(PAGE_OFFSET)
			.fetch();
	}

	@Override
	public List<Buy> getBuysMeByPage(Integer page, Member member) {
		return jpaQueryFactory.selectFrom(buy)
			.where(buy.member.eq(member))
			.offset((page - 1) * PAGE_OFFSET_ME)
			.limit(PAGE_OFFSET_ME)
			.fetch();
	}

	@Override
	public Long getPageNum() {
		Long count = jpaQueryFactory.select(buy.count())
			.from(buy)
			.fetchOne();
		if (count % PAGE_OFFSET != 0) {
			return count / PAGE_OFFSET + 1;
		}
		return count / PAGE_OFFSET;
	}

	@Override
	public Long getBuysMePageNum(Member member) {
		Long count = jpaQueryFactory.select(buy.count())
			.from(buy)
			.where(buy.member.eq(member))
			.fetchOne();
		if (count % PAGE_OFFSET_ME != 0) {
			return count / PAGE_OFFSET_ME + 1;
		}
		return count / PAGE_OFFSET_ME;
	}
}
