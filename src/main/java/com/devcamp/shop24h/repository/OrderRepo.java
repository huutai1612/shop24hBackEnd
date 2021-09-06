package com.devcamp.shop24h.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devcamp.shop24h.getquery.GetOrderWithCustomer;
import com.devcamp.shop24h.model.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {
	@Query(value = "select o.id , o.comments , o.order_date orderDate, o.required_date requiredDate,"
			+ " o.shipped_date shippedDate, o.status , concat(c.first_name, ' ',"
			+ " c.last_name) fullName, c.phone_number phoneNumber\r\n"
			+ "from orders o \r\n"
			+ "join customers c on o.customer_id = c.id  ", nativeQuery = true)
	public List<GetOrderWithCustomer>  getOrderWithCustomer();
}
