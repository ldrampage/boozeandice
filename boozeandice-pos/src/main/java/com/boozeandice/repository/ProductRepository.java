package com.boozeandice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.boozeandice.entity.Product;
import com.boozeandice.entity.ProductCategory;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	public List<Product> findByNameIgnoreCaseLike(String search);
	
	@Query("select product from Product product where product.stocks > 0")
	public List<Product> findByStocksNonZero();
	
	public List<Product> findByProductCategory(ProductCategory category);
}
