package com.boozeandice.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boozeandice.entity.Transaction;
import com.boozeandice.entity.TransactionItem;

@Repository
public interface TransactionItemRepository extends JpaRepository<TransactionItem, Long> {
	public Set<TransactionItem> findByTransaction(Transaction transaction);
}
