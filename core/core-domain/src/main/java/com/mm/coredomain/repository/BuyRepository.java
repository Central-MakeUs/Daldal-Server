package com.mm.coredomain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mm.coredomain.domain.Buy;
import com.mm.coredomain.domain.Member;

public interface BuyRepository extends JpaRepository<Buy, Long> {
	List<Buy> findAllByMember(Member member);
}
