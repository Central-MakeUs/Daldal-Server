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
}
