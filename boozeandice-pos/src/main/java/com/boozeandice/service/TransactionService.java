package com.boozeandice.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boozeandice.entity.CashDrawer;
import com.boozeandice.entity.Transaction;
import com.boozeandice.repository.TransactionRepository;

@Service
public class TransactionService {
	
	private static final Logger logger = LogManager.getLogger(TransactionService.class);
	
	@Autowired
	private TransactionRepository transactionRepo;
	
	public Transaction save(Transaction transaction) {
		return transactionRepo.save(transaction);
	}
	
	public List<Transaction> getAll(){
		return transactionRepo.findAll();
	}
	
	public Transaction getById(Long transactionId) {
		Optional<Transaction> optTrans = transactionRepo.findById(transactionId);
		if(optTrans.isPresent()) {
			return optTrans.get();
		}
		return null;
	}
	
	public Set<Transaction> getByCashDrawer(CashDrawer cashDrawer) {
		return transactionRepo.findByCashdrawer(cashDrawer);
	}
	
	public void delete(Transaction transaction) {
		transactionRepo.delete(transaction);
	}

}
