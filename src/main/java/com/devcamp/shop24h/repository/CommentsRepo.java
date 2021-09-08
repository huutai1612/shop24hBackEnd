package com.devcamp.shop24h.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcamp.shop24h.model.Comments;

public interface CommentsRepo extends JpaRepository<Comments, Integer>{

}
