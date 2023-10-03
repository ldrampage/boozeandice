package com.boozeandice.local.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boozeandice.local.entity.CashDrawer;
import com.boozeandice.local.entity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	
	public List<Expense> findByCashdrawer(CashDrawer cashDrawer);

}
