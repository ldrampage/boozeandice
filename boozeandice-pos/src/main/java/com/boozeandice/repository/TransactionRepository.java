package com.boozeandice.repository;

import java.util.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boozeandice.entity.CashDrawer;
import com.boozeandice.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	public Set<Transaction> findByCashdrawer(CashDrawer cashDrawer);
	public Set<Transaction> findByTransactionDateTimeBetween(Date startDate, Date endDate);
}
