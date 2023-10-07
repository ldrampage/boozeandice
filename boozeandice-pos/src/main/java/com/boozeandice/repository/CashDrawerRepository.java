package com.boozeandice.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boozeandice.local.entity.CashDrawer;

@Repository
public interface CashDrawerRepository extends JpaRepository<CashDrawer, Long> {
	public CashDrawer findByCreatedDateBetween(Date startDate, Date endDate);
}
