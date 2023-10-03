package com.boozeandice.local.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boozeandice.local.entity.POSConfig;

@Repository
public interface POSConfigRepository extends JpaRepository<POSConfig, Long>{
	
	public Optional<POSConfig> findByName(String name);

}
