package com.boozeandice.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boozeandice.local.entity.CashDrawer;
import com.boozeandice.local.entity.Expense;
import com.boozeandice.repository.ExpenseRepository;

@Service
public class ExpenseService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ExpenseRepository expenseRepo;
	
	
	public Expense save(Expense expense) {
		return expenseRepo.save(expense);
	}
	
	public List<Expense> getByCashDrawerToday(CashDrawer cashDrawerToday){
		return expenseRepo.findByCashdrawer(cashDrawerToday);
	}

}
