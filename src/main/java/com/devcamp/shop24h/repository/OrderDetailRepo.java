package com.devcamp.shop24h.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devcamp.shop24h.getquery.GetOrderDetailByOrderId;
import com.devcamp.shop24h.model.OrderDetail;

@Repository
public interface OrderDetailRepo extends JpaRepository<OrderDetail, Integer> {
	@Query(value = "select od.order_id orderId ,od.price_each PriceEach, od.quantity_order quantity, od.product_id productId\r\n"
			+ "from order_details od \r\n"
			+ "where od.order_id  = :orderId", nativeQuery = true)
	List<GetOrderDetailByOrderId> getByOrderId(@Param("orderId") int orderId);
}
