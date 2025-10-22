package com.boozeandice.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boozeandice.entity.JobPosition;
import com.boozeandice.repository.JobPositionRepository;

@Service
public class JobPositionService {
	
	private static final Logger logger= LogManager.getLogger(JobPositionService.class);
	
	@Autowired
	private JobPositionRepository jobPosRepo;
	
	public Set<JobPosition> getAll(){
		Set<JobPosition> set = new HashSet<JobPosition>(jobPosRepo.findAll());
		return set;
	}
	
	public JobPosition getById(Long id) {
		Optional<JobPosition> jobPosition = jobPosRepo.findById(id);
		if(jobPosition.isPresent())
			return jobPosition.get();
		return null;
	}

}
