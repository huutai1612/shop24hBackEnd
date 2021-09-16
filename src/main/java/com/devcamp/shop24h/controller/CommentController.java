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

import com.devcamp.shop24h.model.Comments;
import com.devcamp.shop24h.model.Customer;
import com.devcamp.shop24h.model.Product;
import com.devcamp.shop24h.repository.CommentsRepo;
import com.devcamp.shop24h.repository.CustomerRepo;
import com.devcamp.shop24h.repository.ProductRepo;

@CrossOrigin
@RestController
public class CommentController {
	@Autowired
	CommentsRepo commentsRepo;
	@Autowired
	ProductRepo productRepo;
	@Autowired
	CustomerRepo customerRepo;
	
//	tạo mới comment
	@PostMapping("/customers/{customerId}/products/{productId}/comments")
	public ResponseEntity<Object> createNewComments(
			@PathVariable int productId,
			@PathVariable long customerId,
			@Valid @RequestBody Comments newComments) {
		try {
			Optional<Product> existProduct = productRepo.findById(productId);
			newComments.setProductId(existProduct.get());
			Optional<Customer> customerFound = customerRepo.findById(customerId);
			Customer customer = customerFound.get();
			newComments.setCustomerId(customer);
			newComments.setName(customer.getFirstName() + ' ' + customer.getLastName());
			return new ResponseEntity<>(commentsRepo.save(newComments) ,HttpStatus.CREATED);
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
				return new ResponseEntity<>(commentsRepo.getAverageRates(productId) ,HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/products/{productId}/comments")
	public ResponseEntity<Object> getCommentByProductId(@PathVariable int productId) {
		try {
			Optional<Product> existProduct = productRepo.findById(productId);
			if (existProduct.isPresent()) {				
				return new ResponseEntity<>(commentsRepo.getCommentByProductId(productId) ,HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/comments/{commentId}")
	public ResponseEntity<Object> editComments(@PathVariable int commentId, @Valid Comments newComments) {
		try {
			Optional<Comments> foundComments = commentsRepo.findById(commentId);
			if (foundComments.isPresent()) {	
				Comments updateComments = foundComments.get();
				updateComments.setComments(newComments.getComments());
				updateComments.setRateStar(newComments.getRateStar());
				return new ResponseEntity<>(commentsRepo.save(updateComments) ,HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	xóa tất cả comment
	@DeleteMapping("/comments")
	public ResponseEntity<Object> deleteAllCustomer() {
		try {
			commentsRepo.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	xóa đánh giá dựa trên id
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<Object> deleteCustomerById(@PathVariable int commentId) {
		try {
			Optional<Comments> commentFound = commentsRepo.findById(commentId);
			if (commentFound.isPresent()) {
				commentsRepo.deleteById(commentId);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
