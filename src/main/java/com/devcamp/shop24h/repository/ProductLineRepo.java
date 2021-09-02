package com.devcamp.shop24h.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcamp.shop24h.model.ProductLine;

public interface ProductLineRepo extends JpaRepository<ProductLine, Integer> {
}
