package com.boozeandice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boozeandice.entity.CashDrawer;
import com.boozeandice.entity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	
	public List<Expense> findByCashdrawer(CashDrawer cashDrawer);

}
