package com.mm.coreinfraqdsl.repository;

import com.mm.coredomain.domain.Item;
import com.mm.coredomain.domain.QItem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mm.coredomain.domain.QItem.item;

@Repository
@RequiredArgsConstructor
public class ItemCustomRepositoryImpl implements ItemCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    private final static Long PAGE_OFFSET = 10L;

    @Override
    public List<Item> getItemsByPage(Integer page) {
        return jpaQueryFactory.selectFrom(item)
                .offset((page-1)*PAGE_OFFSET)
                .limit(PAGE_OFFSET)
                .fetch();
    }
}
