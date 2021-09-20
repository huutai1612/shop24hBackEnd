package com.devcamp.shop24h.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devcamp.shop24h.model.Role;
import com.devcamp.shop24h.repository.RoleRepo;

@CrossOrigin
@RestController
public class RoleController {
	@Autowired
	RoleRepo roleRepo;
	
	@GetMapping("/roles")
	public ResponseEntity<Object> getAllRole() {
		try {
			List<Role> roleList = new ArrayList<>();
			roleRepo.findAll().forEach(roleList::add);
			return ResponseEntity.ok(roleList);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
