package com.mm.coreinfraqdsl.repository;

import com.mm.coredomain.domain.Item;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mm.coredomain.domain.QItem.item;

@Repository
@RequiredArgsConstructor
public class SearchCustomRepositoryImpl implements SearchCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    private static final Long PAGE_OFFSET = 10L;

    @Override
    public List<Item> searchItems(Integer page, String keyword) {
        return jpaQueryFactory.selectFrom(item)
                .where(
                        containsKeyword(keyword)
                )
                .offset((page - 1) * PAGE_OFFSET)
                .limit(PAGE_OFFSET)
                .fetch();
    }

    @Override
    public Long getSearchResultNumber(String keyword) {
        return jpaQueryFactory.select(item.count())
                .from(item)
                .where(
                        containsKeyword(keyword)
                )
                .fetchOne();
    }

    private BooleanBuilder containsKeyword(String keyword) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (keyword == null) {
            return null;
        }
        booleanBuilder.or(item.title.contains(keyword));

        return booleanBuilder;
    }

    @Override
    public Long getPageNum(String keyword) {
        Long count = jpaQueryFactory.select(item.count())
                .from(item)
                .where(
                        containsKeyword(keyword)
                )
                .fetchOne();
        if (count == 0) {
            return 1L;
        }
        if (count % PAGE_OFFSET != 0) {
            return count / PAGE_OFFSET + 1;
        }
        return count / PAGE_OFFSET;
    }
}
