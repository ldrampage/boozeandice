package com.boozeandice.local.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boozeandice.local.entity.Transaction;
import com.boozeandice.local.entity.TransactionItem;

@Repository
public interface TransactionItemRepository extends JpaRepository<TransactionItem, Long> {
	public Set<TransactionItem> findByTransaction(Transaction transaction);
}
