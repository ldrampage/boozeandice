package com.boozeandice.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
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
	
	public Set<Transaction> getAll(){
		Set<Transaction> setT = new HashSet<>(transactionRepo.findAll());
		return setT;
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
	
	public Set<Transaction> getByTransactionDate(Date date) {
		Calendar start = Calendar.getInstance();
		start.setTime(date);
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		start.set(Calendar.MILLISECOND, 0);
		Calendar end = Calendar.getInstance();
		end.setTime(date);
		end.set(Calendar.HOUR_OF_DAY, 23);
		end.set(Calendar.MINUTE, 59);
		end.set(Calendar.SECOND, 59);
		end.set(Calendar.MILLISECOND, 999);
		return transactionRepo.findByTransactionDateTimeBetween(start.getTime(), end.getTime());
	}
	
	public Set<Transaction> getByToday() {
		Calendar startOfDay = Calendar.getInstance();
        startOfDay.setTime(new Date());
        startOfDay.set(Calendar.HOUR_OF_DAY, 0);
        startOfDay.set(Calendar.MINUTE, 0);
        startOfDay.set(Calendar.SECOND, 0);
        startOfDay.set(Calendar.MILLISECOND, 0);

        Calendar endOfDay = Calendar.getInstance();
        endOfDay.setTime(new Date()); 
        endOfDay.set(Calendar.HOUR_OF_DAY, 23);
        endOfDay.set(Calendar.MINUTE, 59);
        endOfDay.set(Calendar.SECOND, 59);
        endOfDay.set(Calendar.MILLISECOND, 999);
		return transactionRepo.findByTransactionDateTimeBetween(startOfDay.getTime(), endOfDay.getTime());
	}

}
