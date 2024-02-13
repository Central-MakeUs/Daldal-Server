package com.mm.coreinfraqdsl.repository;

import com.mm.coredomain.domain.Item;
import com.mm.coredomain.domain.ItemCategoryType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mm.coredomain.domain.QItem.item;

@Repository
@RequiredArgsConstructor
public class ItemCustomRepositoryImpl implements ItemCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    private static final Long PAGE_OFFSET = 10L;
    private static final Long SUGGESTED_ITEMS_OFFSET = 3L;

    @Override
    public List<Item> getItemsByPage(Integer page, String category) {
        return jpaQueryFactory.selectFrom(item)
                .where(isCategory(category))
                .orderBy(item.id.desc())
                .offset((page - 1) * PAGE_OFFSET)
                .limit(PAGE_OFFSET)
                .fetch();
    }

    @Override
    public List<Item> getSuggestedItems() {
        return jpaQueryFactory.selectFrom(item)
                .where(item.isSuggested.eq(true))
                .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                .limit(SUGGESTED_ITEMS_OFFSET)
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
        if (count == 0) {
            return 1L;
        }
        if (count % PAGE_OFFSET != 0) {
            return count / PAGE_OFFSET + 1;
        }
        return count / PAGE_OFFSET;
    }
}
