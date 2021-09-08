package com.devcamp.shop24h.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.devcamp.shop24h.model.Comments;
import com.devcamp.shop24h.repository.CommentsRepo;
import com.devcamp.shop24h.repository.ProductRepo;

@CrossOrigin
@RestController
public class CommentController {
	@Autowired
	CommentsRepo commentsRepo;
	@Autowired
	ProductRepo productRepo;
	
	@PostMapping("/products/{productId}/comments")
	public ResponseEntity<Object> createNewComments(@PathVariable int productId,@Valid @RequestBody Comments newComments) {
		try {
			newComments.setProductId(productRepo.findById(productId).get());
			return new ResponseEntity<>(commentsRepo.save(newComments) ,HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
