package com.boozeandice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boozeandice.local.entity.Product;
import com.boozeandice.local.entity.ProductStock;

@Repository
public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {
	public List<ProductStock> findByProduct(Product product);
	public ProductStock findByBarcodeDigits(Long barcodeDigits);
}
