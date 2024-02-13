package com.mm.coreinfraqdsl.repository;

import com.mm.coredomain.domain.Dib;
import com.mm.coredomain.domain.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mm.coredomain.domain.QDib.dib;

@Repository
@RequiredArgsConstructor
public class DibCustomRepositoryImpl implements DibCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    private static final Long PAGE_OFFSET = 10L;

    @Override
    public List<Dib> getDibsByPage(Integer page, Member member) {
        return jpaQueryFactory.selectFrom(dib)
                .where(dib.member.eq(member))
                .offset((page - 1) * PAGE_OFFSET)
                .limit(PAGE_OFFSET)
                .fetch();
    }

    @Override
    public Long getPageNum(Member member) {
        Long count = jpaQueryFactory.select(dib.count())
                .from(dib)
                .where(dib.member.eq(member))
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
