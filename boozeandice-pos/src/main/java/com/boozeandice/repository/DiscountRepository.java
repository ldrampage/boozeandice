package com.boozeandice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boozeandice.entity.Discount;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {

}
