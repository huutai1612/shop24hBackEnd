package com.devcamp.shop24h.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devcamp.shop24h.model.Office;

@Repository
public interface OfficeRepo extends JpaRepository<Office, Integer> {
}
