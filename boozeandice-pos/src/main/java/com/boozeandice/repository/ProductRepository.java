package com.boozeandice.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.boozeandice.entity.Product;
import com.boozeandice.entity.ProductCategory;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	public Set<Product> findByNameIgnoreCaseLike(String search);
		
	@Query("select product from Product product where product.stocks > 0")
	public Set<Product> findByStocksNonZero();
	
	public Set<Product> findByProductCategory(ProductCategory category);
	
	public Set<Product> findByProductCategoryAndStocksGreaterThan(ProductCategory category, Long stock);
	
	public Set<Product> findByNameContainingAndStocksGreaterThan(String productName, Long stock);
	
	//@Query("select product from Product product where product.name like %:productName% and product.stocks > 0")
	public Set<Product> findByNameContaining(String productName);


}
