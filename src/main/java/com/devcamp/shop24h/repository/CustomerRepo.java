package com.devcamp.shop24h.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devcamp.shop24h.getquery.GetCustomerCountOrder;
import com.devcamp.shop24h.getquery.GetTotalPaymentCustomer;
import com.devcamp.shop24h.model.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {
	Optional<Customer> findByPhoneNumber(String phoneNumber);

	@Query(value = "select concat(c.first_name, ' ', c.last_name) fullName , count(o.id) totalOrder \r\n"
			+ "from orders o \r\n" + "join customers c on o.customer_id = c.id \r\n"
			+ "group by c.id", nativeQuery = true)
	public List<GetCustomerCountOrder> getCountCustomerOrder();

	@Query(value = "select concat(c.first_name, ' ', c.last_name) fullName, c.phone_number phoneNumber, total.totalPayment\r\n"
			+ "from customers c \r\n" + "join\r\n" + "(select sum(p.ammount) totalPayment, p.customer_id customerId\r\n"
			+ "from payments p \r\n" + "group by p.customer_id ) total on total.customerId = c.id \r\n"
			+ "where totalPayment > 50000000", nativeQuery = true)
	public List<GetTotalPaymentCustomer> getPlatinumCustomers();

	@Query(value = "select concat(c.first_name, ' ', c.last_name) fullName, c.phone_number phoneNumber, total.totalPayment\r\n"
			+ "from customers c \r\n" + "join\r\n" + "(select sum(p.ammount) totalPayment, p.customer_id customerId\r\n"
			+ "from payments p \r\n" + "group by p.customer_id ) total on total.customerId = c.id \r\n"
			+ "where totalPayment < 50000000 and totalPayment > 20000000", nativeQuery = true)
	public List<GetTotalPaymentCustomer> getGoldCustomers();

	@Query(value = "select concat(c.first_name, ' ', c.last_name) fullName, c.phone_number phoneNumber, total.totalPayment\r\n"
			+ "from customers c \r\n" + "join\r\n" + "(select sum(p.ammount) totalPayment, p.customer_id customerId\r\n"
			+ "from payments p \r\n" + "group by p.customer_id ) total on total.customerId = c.id \r\n"
			+ "where totalPayment < 20000000 and totalPayment > 10000000", nativeQuery = true)
	public List<GetTotalPaymentCustomer> getSilverCustomers();
	
	@Query(value = "select concat(c.first_name, ' ', c.last_name) fullName, c.phone_number phoneNumber, total.totalPayment\r\n"
			+ "from customers c \r\n" + "join\r\n" + "(select sum(p.ammount) totalPayment, p.customer_id customerId\r\n"
			+ "from payments p \r\n" + "group by p.customer_id ) total on total.customerId = c.id \r\n"
			+ "where totalPayment < 10000000 and totalPayment > 5000000", nativeQuery = true)
	public List<GetTotalPaymentCustomer> getVipCustomer();
}
