package com.mm.coreinfraqdsl.repository;

import static com.mm.coredomain.domain.QItem.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mm.coredomain.domain.Item;
import com.mm.coredomain.domain.ItemCategoryType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ItemCustomRepositoryImpl implements ItemCustomRepository {
	private final JPAQueryFactory jpaQueryFactory;

	private static final Long PAGE_OFFSET = 10L;

	@Override
	public List<Item> getItemsByPage(Integer page, String category) {
		return jpaQueryFactory.selectFrom(item)
			.where(isCategory(category))
			.orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
			.offset((page - 1) * PAGE_OFFSET)
			.limit(PAGE_OFFSET)
			.fetch();
	}

	private BooleanExpression isCategory(String categoryType) {
		if (categoryType == null) {
			return null;
		}

		return item.categoryType.eq(ItemCategoryType.of(categoryType));
	}

	@Override
	public Long getPageNum(String category) {
		Long count = jpaQueryFactory.select(item.count())
			.from(item)
			.where(isCategory(category))
			.fetchOne();
		if (count % PAGE_OFFSET != 0) {
			return count / PAGE_OFFSET + 1;
		}
		return count / PAGE_OFFSET;
	}
}
