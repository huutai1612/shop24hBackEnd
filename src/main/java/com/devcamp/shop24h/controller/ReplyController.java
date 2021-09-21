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
import com.devcamp.shop24h.model.ReplyComment;
import com.devcamp.shop24h.repository.CommentsRepo;
import com.devcamp.shop24h.repository.ReplyRepo;

@CrossOrigin
@RestController
public class ReplyController {
	@Autowired
	ReplyRepo replyRepo;
	
	@Autowired
	CommentsRepo commentsRepo;
	
	@GetMapping("/replies")
	public ResponseEntity<Object> getAllReply() {
		try {
			return new ResponseEntity<>(replyRepo.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/comments/{commentId}/replies")
	public ResponseEntity<Object> createReply(@PathVariable int commentId,@Valid @RequestBody ReplyComment newReply) {
		try {
			Optional<Comments> commentFound = commentsRepo.findById(commentId);
			if (commentFound.isPresent()) {
				Comments existComments = commentFound.get();
				newReply.setCommentId(existComments);
				replyRepo.save(newReply);
				return new ResponseEntity<Object>(newReply, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
