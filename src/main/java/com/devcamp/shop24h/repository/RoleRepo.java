package com.devcamp.shop24h.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcamp.shop24h.model.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {

}
