package com.devcamp.shop24h.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devcamp.shop24h.getquery.GetAverageRate;
import com.devcamp.shop24h.getquery.GetComments;
import com.devcamp.shop24h.model.Comments;

@Repository
public interface CommentsRepo extends JpaRepository<Comments, Integer>{
	@Query(value = "select (rateStar / totalRate) average\r\n"
			+ "from \r\n"
			+ "(select sum(c.rate_star) rateStar, count(c.id) totalRate\r\n"
			+ "from comments c \r\n"
			+ "join products p on c.product_id = p.id and p.id = :productId) a", nativeQuery = true)
	public List<GetAverageRate> getAverageRates(@Param("productId") int productId);
	
	@Query(value = "select c.id, c.comments , c.name , c.rate_star rateStar, c.product_id productId, c.customer_id  customerId\r\n"
			+ "from comments c \r\n"
			+ "where c.product_id = :productId", nativeQuery = true)
	public List<GetComments> getCommentByProductId(@Param("productId") int productId);
	
	@Query(value = "select c.id, c.comments , c.name , c.rate_star rateStar, c.product_id productId, c.customer_id  customerId\r\n"
			+ "from comments c \r\n", nativeQuery = true)
	public List<GetComments> getAllComments();
}
