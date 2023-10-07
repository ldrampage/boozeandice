package com.boozeandice.service;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boozeandice.local.entity.CashDrawer;
import com.boozeandice.repository.CashDrawerRepository;

@Service
public class CashDrawerService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LogManager.getLogger(CashDrawerService.class);
	
	@Autowired
	private CashDrawerRepository cashDrawerRepo;	
	
	public CashDrawer getByToday() {
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
		return cashDrawerRepo.findByCreatedDateBetween(startOfDay.getTime(), endOfDay.getTime());
	}
	
	public CashDrawer save(CashDrawer cashDrawer) {
		return cashDrawerRepo.save(cashDrawer);
	}

	
}
