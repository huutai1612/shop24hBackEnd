package com.devcamp.shop24h.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devcamp.shop24h.getquery.GetPayment;
import com.devcamp.shop24h.model.Payment;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Integer> {
	@Query(value = "select p.payment_date paymentDate, week(p.payment_date) week, sum(p.ammount) total, month(p.payment_date) month\r\n"
			+ "from payments p \r\n"
			+ "group by paymentDate ", nativeQuery = true)
	public List<GetPayment> getByDate();
	
	@Query(value = "select p.payment_date paymentDate, week(p.payment_date) week, sum(p.ammount) total, month(p.payment_date) month\r\n"
			+ "from payments p \r\n"
			+ "group by week ", nativeQuery = true)
	public List<GetPayment>  getByWeek();
	
	@Query(value = "select p.payment_date paymentDate, week(p.payment_date) week, sum(p.ammount) total, month(p.payment_date) month\r\n"
			+ "from payments p \r\n"
			+ "group by month ", nativeQuery = true)
	public List<GetPayment>  getByMonth();
}
