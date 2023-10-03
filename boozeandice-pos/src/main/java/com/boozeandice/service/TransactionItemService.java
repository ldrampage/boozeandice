package com.boozeandice.service;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boozeandice.local.entity.Transaction;
import com.boozeandice.local.entity.TransactionItem;
import com.boozeandice.local.repository.TransactionItemRepository;

@Service
public class TransactionItemService {
	
	private static final Logger logger = LogManager.getLogger(TransactionItemService.class);
	
	@Autowired
	private TransactionItemRepository transactionItemRepo;
	
	public Set<TransactionItem> getByTransaction(Transaction transaction){
		return transactionItemRepo.findByTransaction(transaction);
	}
	
	public TransactionItem save(TransactionItem transactionItem) {
		return transactionItemRepo.save(transactionItem);
	}
	
	public void deleteAll(Set<TransactionItem> transactionItem) {
		transactionItemRepo.deleteAll(transactionItem);
	}

}
