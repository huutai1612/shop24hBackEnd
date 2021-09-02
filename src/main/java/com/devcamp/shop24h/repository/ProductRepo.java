package com.devcamp.shop24h.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devcamp.shop24h.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
	@Query(value = "select *\n"
			+ "from products p \n"
			+ "where p.is_related = 1" , nativeQuery = true)
	List<Product> getLatestProduct();
}
