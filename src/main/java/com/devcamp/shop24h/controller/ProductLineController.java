package com.devcamp.shop24h.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.devcamp.shop24h.model.ProductLine;
import com.devcamp.shop24h.repository.ProductLineRepo;

@RestController
@CrossOrigin
public class ProductLineController {
	@Autowired
	ProductLineRepo productLineRepo;
	
//	lấy product line
	@GetMapping("/product-lines")
	public ResponseEntity<Object> getAllProductLines() {
		try {
			return new ResponseEntity<>(productLineRepo.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	lấy product line dựa trên id
	@GetMapping("/product-lines/{productLineId}")
	public ResponseEntity<Object> getProductLineById(@PathVariable Integer productLineId) {
		try {
			Optional<ProductLine> productLineFound = productLineRepo.findById(productLineId);
			if (productLineFound.isPresent()) {
				return new ResponseEntity<>(productLineFound.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	tạo mới product line
	@PostMapping("/product-lines")
	public ResponseEntity<Object> createProductLine(@Valid @RequestBody ProductLine newProductLine) {
		try {
			return new ResponseEntity<>(productLineRepo.save(newProductLine), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	sửa product line
	@PutMapping("/product-lines/{productLineId}")
	public ResponseEntity<Object> updateProductLine(@PathVariable Integer productLineId
			,@Valid @RequestBody ProductLine newProductLine) {
		try {
			Optional<ProductLine> productLineFound = productLineRepo.findById(productLineId);
			if (productLineFound.isPresent()) {
				ProductLine updateProDuctLine = productLineFound.get();
				updateProDuctLine.setDescription(newProductLine.getDescription());
				updateProDuctLine.setProductLine(newProductLine.getProductLine());
				return new ResponseEntity<>(productLineRepo.save(updateProDuctLine), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	xóa product line by id
	@DeleteMapping("/product-lines/{productLineId}")
	public ResponseEntity<Object> deleteProductLineById(@PathVariable Integer productLineId) {
		try {
			Optional<ProductLine> productLineFound = productLineRepo.findById(productLineId);
			if (productLineFound.isPresent()) {
				productLineRepo.deleteById(productLineId);
				return new ResponseEntity<>( HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	xóa tất cả product line
	@DeleteMapping("/product-lines")
	public ResponseEntity<Object> deleteAllProductLine() {
		try {
			productLineRepo.deleteAll();
			return new ResponseEntity<>( HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
