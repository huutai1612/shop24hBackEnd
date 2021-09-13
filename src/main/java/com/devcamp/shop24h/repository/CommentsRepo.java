package com.devcamp.shop24h.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devcamp.shop24h.getquery.GetAverageRate;
import com.devcamp.shop24h.model.Comments;

@Repository
public interface CommentsRepo extends JpaRepository<Comments, Integer>{
	@Query(value = "select (rateStar / totalRate) average\r\n"
			+ "from \r\n"
			+ "(select sum(c.rate_star) rateStar, count(c.id) totalRate\r\n"
			+ "from comments c \r\n"
			+ "join products p on c.product_id = p.id and p.id = :productId) a", nativeQuery = true)
	public List<GetAverageRate> getAverageRates(@Param("productId") int productId);
}
