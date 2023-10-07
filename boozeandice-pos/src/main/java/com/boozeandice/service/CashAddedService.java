package com.boozeandice.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boozeandice.local.entity.CashAdded;
import com.boozeandice.repository.CashAddedRepository;

@Service
public class CashAddedService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private CashAddedRepository cashAddedRepo;
	
	public CashAdded save(CashAdded cashAdded) {
		return cashAddedRepo.save(cashAdded);
	}

}
