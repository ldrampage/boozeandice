package com.boozeandice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boozeandice.entity.JobPosition;


@Repository
public interface JobPositionRepository extends JpaRepository<JobPosition, Long>{

}
