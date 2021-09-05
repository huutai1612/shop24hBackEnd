package com.devcamp.shop24h.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devcamp.shop24h.model.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {
	Optional<Customer> findByPhoneNumber(String phoneNumber);
}
