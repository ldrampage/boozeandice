package com.boozeandice.local.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boozeandice.local.entity.CashAdded;

@Repository
public interface CashAddedRepository extends JpaRepository<CashAdded, Long> {

}
