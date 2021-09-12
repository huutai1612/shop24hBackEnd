package com.devcamp.shop24h.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
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

import com.devcamp.shop24h.getquery.GetPayment;
import com.devcamp.shop24h.model.Customer;
import com.devcamp.shop24h.model.Payment;
import com.devcamp.shop24h.repository.CustomerRepo;
import com.devcamp.shop24h.repository.PaymentRepo;
import com.devcamp.shop24h.service.PaymentExcelExporterByDate;
import com.devcamp.shop24h.service.PaymentExcelExporterByMonth;
import com.devcamp.shop24h.service.PaymentExcelExporterByWeek;

@CrossOrigin
@RestController
public class PaymentController {
	@Autowired
	PaymentRepo paymentRepo;
	@Autowired
	CustomerRepo customerRepo;
	
	@GetMapping("/payments")
	public ResponseEntity<Object> getAllPayments() {
		try {
			return new ResponseEntity<>(paymentRepo.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
 	
	@GetMapping("/payments/{paymentId}")
	public ResponseEntity<Object> getPaymentById(@PathVariable Integer paymentId) {
		try {
			Optional<Payment> paymentFound = paymentRepo.findById(paymentId);
			if (paymentFound.isPresent()) {
				return new ResponseEntity<>(paymentFound.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/payment/excel/export/dates")
	public void exportDatePayment(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=date" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        
        List<GetPayment> paymentList = new ArrayList<>();
        paymentRepo.getByDate().forEach(paymentList::add);
        PaymentExcelExporterByDate excelExporter = new PaymentExcelExporterByDate(paymentList);
        excelExporter.export(response); 
	}
	
	@GetMapping("/payment/excel/export/weeks")
	public void exportWeekPayment(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=payment_week" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        
        List<GetPayment> paymentList = new ArrayList<>();
        paymentRepo.getByWeek().forEach(paymentList::add);
        PaymentExcelExporterByWeek excelExporter = new PaymentExcelExporterByWeek(paymentList);
        excelExporter.export(response); 
	}
	
	@GetMapping("/payment/excel/export/months")
	public void exportMonthPayment(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=payment_month" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        
        List<GetPayment> paymentList = new ArrayList<>();
        paymentRepo.getByMonth().forEach(paymentList::add);
        PaymentExcelExporterByMonth excelExporter = new PaymentExcelExporterByMonth(paymentList);
        excelExporter.export(response); 
	}
	
	@GetMapping("/payments/dates/{firstDate}/{lastDate}")
	public ResponseEntity<Object> getPaymentDate(@PathVariable String firstDate,
			@PathVariable String lastDate) {
		try {
			return new ResponseEntity<>(paymentRepo.getByDateRange(firstDate, lastDate), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/payments/weeks")
	public ResponseEntity<Object> getPaymentWeek() {
		try {
			return new ResponseEntity<>(paymentRepo.getByWeek(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/payments/months")
	public ResponseEntity<Object> getPaymentMonth() {
		try {
			return new ResponseEntity<>(paymentRepo.getByMonth(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/customers/{customerId}/payments")
	public ResponseEntity<Object> createPayment(@Valid @RequestBody Payment newPayment, @PathVariable int customerId) {
		try {
			Optional<Customer> customerFound = customerRepo.findById(customerId);
			newPayment.setCustomerId(customerFound.get());
			return new ResponseEntity<>(paymentRepo.save(newPayment), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/payments/{paymentId}")
	public ResponseEntity<Object> updatePayment(@PathVariable Integer paymentId, @Valid @RequestBody Payment newPayment) {
		try {
			Optional<Payment> paymentFound = paymentRepo.findById(paymentId);
			if (paymentFound.isPresent()) {
				Payment updatePayment = paymentFound.get();
				updatePayment.setAmount(newPayment.getAmount());
				updatePayment.setCheckNumber(newPayment.getCheckNumber());
				updatePayment.setPaymentDate(newPayment.getPaymentDate());
				return new ResponseEntity<>(paymentRepo.save(updatePayment), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@DeleteMapping("/payments/{paymentId}")
	public ResponseEntity<Object> deletePaymentById(@PathVariable Integer paymentId) {
		try {
			Optional<Payment> paymentFound = paymentRepo.findById(paymentId);
			if (paymentFound.isPresent()) {
				paymentRepo.deleteById(paymentId);
				return new ResponseEntity<>( HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/payments")
	public ResponseEntity<Object> deleteAllPayment() {
		try {
			paymentRepo.deleteAll();
			return new ResponseEntity<>( HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
