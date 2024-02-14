package com.mm.coredomain.repository;

import com.mm.coredomain.domain.Dib;
import com.mm.coredomain.domain.Item;
import com.mm.coredomain.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DibRepository extends JpaRepository<Dib, Long> {
    Optional<Dib> findByMemberAndItem(Member member, Item item);

    Long countByMember(Member member);
}
