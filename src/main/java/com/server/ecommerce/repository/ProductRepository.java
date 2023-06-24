package com.server.ecommerce.repository;

import com.server.ecommerce.model.Category;
import com.server.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
	
	Page<Product> findByCategory(Category category,Pageable pageable);
	List<Product> findByProductNameContaining(String pName);
	
	

}
