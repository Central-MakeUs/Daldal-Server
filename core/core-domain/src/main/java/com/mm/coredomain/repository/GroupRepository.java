package com.mm.coredomain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mm.coredomain.domain.Groups;

public interface GroupRepository extends JpaRepository<Groups, Long> {
	Optional<Groups> findByName(String name);
}
