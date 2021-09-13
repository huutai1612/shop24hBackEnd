package com.devcamp.shop24h.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.devcamp.shop24h.model.Comments;
import com.devcamp.shop24h.model.Product;
import com.devcamp.shop24h.repository.CommentsRepo;
import com.devcamp.shop24h.repository.ProductRepo;

@CrossOrigin
@RestController
public class CommentController {
	@Autowired
	CommentsRepo commentsRepo;
	@Autowired
	ProductRepo productRepo;
	
//	tạo mới comment
	@PostMapping("/products/{productId}/comments")
	public ResponseEntity<Object> createNewComments(@PathVariable int productId,@Valid @RequestBody Comments newComments) {
		try {
			Optional<Product> existProduct = productRepo.findById(productId);
			if (existProduct.isPresent()) {				
				newComments.setProductId(existProduct.get());
				return new ResponseEntity<>(commentsRepo.save(newComments) ,HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	tính rating trung bình của comment
	@GetMapping("/products/{productId}/comments/average")
	public ResponseEntity<Object> getAverageRate(@PathVariable int productId) {
		try {
			Optional<Product> existProduct = productRepo.findById(productId);
			if (existProduct.isPresent()) {				
				return new ResponseEntity<>(commentsRepo.getAverageRates(productId) ,HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
