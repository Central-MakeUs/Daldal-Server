package com.mm.coredomain.repository;

import com.mm.coredomain.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
