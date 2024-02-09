package com.mm.coreinfraqdsl.repository;

import static com.mm.coredomain.domain.QBuy.*;
import static com.mm.coredomain.domain.QItem.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mm.coredomain.domain.Buy;
import com.mm.coredomain.domain.Item;
import com.mm.coredomain.domain.Member;
import com.mm.coredomain.domain.RefundStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AdminCustomRepositoryImpl implements AdminCustomRepository {
	private final JPAQueryFactory jpaQueryFactory;
	private static final Long PAGE_OFFSET = 10L;

	@Override
	public List<Item> getItemsByPage(Integer page) {
		return jpaQueryFactory.selectFrom(item)
			.orderBy(item.id.desc())
			.offset((page - 1) * PAGE_OFFSET)
			.limit(PAGE_OFFSET)
			.fetch();
	}

	@Override
	public Long getItemsPageNum() {
		Long count = jpaQueryFactory.select(item.count())
			.from(item)
			.fetchOne();
		if (count % PAGE_OFFSET != 0) {
			return count / PAGE_OFFSET + 1;
		}
		return count / PAGE_OFFSET;
	}

	@Override
	public List<Buy> getBuysAdminByPage(Integer page) {
		return jpaQueryFactory.selectFrom(buy)
			.where(isBuy())
			.orderBy(buy.id.desc())
			.offset((page - 1) * PAGE_OFFSET)
			.limit(PAGE_OFFSET)
			.fetch();
	}

	@Override
	public Long getBuysAdminPageNum() {
		Long count = jpaQueryFactory.select(buy.count())
			.from(buy)
			.where(isBuy())
			.fetchOne();
		if (count % PAGE_OFFSET != 0) {
			return count / PAGE_OFFSET + 1;
		}
		return count / PAGE_OFFSET;
	}

	@Override
	public List<Buy> getWithdrawsAdminByPage(Integer page) {
		return jpaQueryFactory.selectFrom(buy)
			.where(isWithdraw())
			.orderBy(buy.id.desc())
			.offset((page - 1) * PAGE_OFFSET)
			.limit(PAGE_OFFSET)
			.fetch();
	}

	@Override
	public Long getWithdrawsAdminPageNum() {
		Long count = jpaQueryFactory.select(buy.count())
			.from(buy)
			.where(isWithdraw())
			.fetchOne();
		if (count % PAGE_OFFSET != 0) {
			return count / PAGE_OFFSET + 1;
		}
		return count / PAGE_OFFSET;
	}

	@Override
	public List<Buy> getWithdrawsAdminByPageByMember(Integer page, Member member) {
		return jpaQueryFactory.selectFrom(buy)
			.where(isWithdraw(), buy.member.eq(member))
			.offset((page - 1) * PAGE_OFFSET)
			.limit(PAGE_OFFSET)
			.fetch();
	}

	@Override
	public Long getWithdrawsAdminPageNumByMember(Member member) {
		Long count = jpaQueryFactory.select(buy.count())
			.from(buy)
			.where(isWithdraw(), buy.member.eq(member))
			.fetchOne();
		if (count % PAGE_OFFSET != 0) {
			return count / PAGE_OFFSET + 1;
		}
		return count / PAGE_OFFSET;
	}

	private BooleanExpression isBuy() {
		return buy.refundStatus.in(RefundStatus.IN_PROGRESS, RefundStatus.COMPLETED, RefundStatus.REJECTED);
	}

	private BooleanExpression isWithdraw() {
		return buy.refundStatus.in(RefundStatus.WITHDRAWN_IN_PROGRESS, RefundStatus.WITHDRAWN_COMPLETED,
			RefundStatus.WITHDRAWN_REJECTED);
	}
}
