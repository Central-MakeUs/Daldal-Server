package com.mm.coredomain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mm.coredomain.domain.Dib;
import com.mm.coredomain.domain.Item;
import com.mm.coredomain.domain.Member;

public interface DibRepository extends JpaRepository<Dib, Long> {
	Optional<Dib> findByMemberAndItem(Member member, Item item);
}
