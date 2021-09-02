package com.devcamp.shop24h.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devcamp.shop24h.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
	@Query(value = "select *\n"
			+ "from products p \n"
			+ "where p.product_line_id = :productLineId" , nativeQuery = true)
	List<Product> getByProductLineId(@Param("productLineId") int productLineId);
	
}
