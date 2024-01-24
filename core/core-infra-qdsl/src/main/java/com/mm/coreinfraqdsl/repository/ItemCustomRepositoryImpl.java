package com.mm.coreinfraqdsl.repository;

import static com.mm.coredomain.domain.QItem.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mm.coredomain.domain.Item;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ItemCustomRepositoryImpl implements ItemCustomRepository {
	private final JPAQueryFactory jpaQueryFactory;

	private static final Long PAGE_OFFSET = 10L;

	@Override
	public List<Item> getItemsByPage(Integer page) {
		return jpaQueryFactory.selectFrom(item)
			.offset((page - 1) * PAGE_OFFSET)
			.limit(PAGE_OFFSET)
			.fetch();
	}
}
