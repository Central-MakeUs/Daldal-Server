package com.mm.coreinfraqdsl.repository;

import com.mm.coredomain.domain.Buy;
import com.mm.coredomain.domain.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mm.coredomain.domain.QBuy.buy;

@Repository
@RequiredArgsConstructor
public class BuyCustomRepositoryImpl implements BuyCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    private static final Long PAGE_OFFSET = 10L;
    private static final Long PAGE_OFFSET_ME = 9L;

    @Override
    public List<Buy> getBuysMeByMember(Integer page, Member member) {
        return jpaQueryFactory.selectFrom(buy)
                .where(buy.member.eq(member))
                .orderBy(buy.id.desc())
                .offset((page - 1) * PAGE_OFFSET_ME)
                .limit(PAGE_OFFSET_ME)
                .fetch();
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
