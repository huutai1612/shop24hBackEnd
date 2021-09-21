package com.devcamp.shop24h.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.management.relation.RoleList;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devcamp.shop24h.getquery.GetTotalPaymentCustomer;
import com.devcamp.shop24h.model.Customer;
import com.devcamp.shop24h.model.Role;
import com.devcamp.shop24h.repository.CustomerRepo;
import com.devcamp.shop24h.repository.RoleRepo;
import com.devcamp.shop24h.security.UserPrincipal;
import com.devcamp.shop24h.service.TypeCustomerExcelExporter;

@CrossOrigin
@RestController
public class CustomerController {
	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	private RoleRepo roleRepo;

	private Long G_CUSTOMER_ID = 3L;

//	lấy tất cả khách hàng
	@GetMapping("/customers")
	public ResponseEntity<Object> getAllCustomer() {
		try {
			return new ResponseEntity<>(customerRepo.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	get customer by role
	@GetMapping("/customers/roles/{roleId}")
	public ResponseEntity<Object> getCustomerByRole(@PathVariable long roleId) {
		try {
			return ResponseEntity.ok(customerRepo.getCustomerByrole(roleId));
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	has authority
//	get info khach hang
	@PreAuthorize("hasAuthority('READ')")
	@GetMapping("/user-info")
	public ResponseEntity<Object> getUserInfo() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserPrincipal user = Optional.of(((UserPrincipal) authentication.getPrincipal())).get();
			List<Object> listInfo = new ArrayList<>();
			listInfo.add(user.getUsername());
			listInfo.add(user.getUserId());
			return ResponseEntity.ok(listInfo);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	check role
	@PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("/user/checkrole")
    public ResponseEntity<Object> checkRole() {
    	try {
			return ResponseEntity.ok("customer");
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

//	xuất file excel khách hàng platinum
	@GetMapping("/type-customer/excel/export/platinum")
	public void exportPlatinumCustomer(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=type_platinum" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);

		List<GetTotalPaymentCustomer> platinumCustomerList = new ArrayList<>();
		customerRepo.getPlatinumCustomers().forEach(platinumCustomerList::add);
		TypeCustomerExcelExporter excelExporter = new TypeCustomerExcelExporter(platinumCustomerList);
		excelExporter.export(response);
	}

//	xuất file excel khách hàng gold
	@GetMapping("/type-customer/excel/export/gold")
	public void exportGoldCustomer(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=type_gold" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);

		List<GetTotalPaymentCustomer> platinumCustomerList = new ArrayList<>();
		customerRepo.getGoldCustomers().forEach(platinumCustomerList::add);
		TypeCustomerExcelExporter excelExporter = new TypeCustomerExcelExporter(platinumCustomerList);
		excelExporter.export(response);
	}

//	xuất file excel khách hàng silver
	@GetMapping("/type-customer/excel/export/silver")
	public void exportSilverCustomer(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=type_silver" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);

		List<GetTotalPaymentCustomer> platinumCustomerList = new ArrayList<>();
		customerRepo.getSilverCustomers().forEach(platinumCustomerList::add);
		TypeCustomerExcelExporter excelExporter = new TypeCustomerExcelExporter(platinumCustomerList);
		excelExporter.export(response);
	}

//	xuất file excel khách hàng vip
	@GetMapping("/type-customer/excel/export/vip")
	public void exportVipCustomer(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=type_vip" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);

		List<GetTotalPaymentCustomer> platinumCustomerList = new ArrayList<>();
		customerRepo.getVipCustomer().forEach(platinumCustomerList::add);
		TypeCustomerExcelExporter excelExporter = new TypeCustomerExcelExporter(platinumCustomerList);
		excelExporter.export(response);
	}

//	đếm số order của khách hàng
	@GetMapping("/customers/count-orders")
	public ResponseEntity<Object> getCustomerCountOrder() {
		try {
			return new ResponseEntity<>(customerRepo.getCountCustomerOrder(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	lọc số order của khách hàng theo ngày
	@GetMapping("/customers/filter-count-orders")
	public ResponseEntity<Object> countOrderOfCustomerByDate(@RequestParam String firstDate,
			@RequestParam String lastDate) {
		try {
			return new ResponseEntity<>(customerRepo.filterCountCustomerOrder(firstDate, lastDate), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	lấy khách hàng dựa trên id
	@GetMapping("/customers/{customerId}")
	public ResponseEntity<Object> getCustomerById(@PathVariable Long customerId) {
		try {
			Optional<Customer> customerFound = customerRepo.findById(customerId);
			if (customerFound.isPresent()) {
				return new ResponseEntity<>(customerFound.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	lấy khách hàng dựa trên số điện thoại
	@GetMapping("/customers/phone/{phoneNumber}")
	public ResponseEntity<Object> getCustomerByPhone(@PathVariable String phoneNumber) {
		try {
			Customer customerFound = customerRepo.findByPhoneNumber(phoneNumber);
			if (customerFound != null) {
				return new ResponseEntity<>(customerFound, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	tạo mới khách hàng
	@PostMapping("/customers")
	public ResponseEntity<Object> createCustomer(@Valid @RequestBody Customer newCustomer) {
		try {
			newCustomer.setPassword("$2a$10$LiQfGYKQ7eLdHKIZhmmbguw4B2ch9pYdEdjLMXBGCeda3pxtIdfR6");
			newCustomer.setUsername(newCustomer.getPhoneNumber());
			newCustomer.setRoles(Arrays.asList(roleRepo.findById(G_CUSTOMER_ID).get()));
			return new ResponseEntity<>(customerRepo.save(newCustomer), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	sửa thông tin khách hàng
	@PutMapping("/customers/{customerId}")
	public ResponseEntity<Object> updateCustomer(@Valid @RequestBody Customer newCustomer,
			@PathVariable Long customerId) {
		try {
			Optional<Customer> customerFound = customerRepo.findById(customerId);
			if (customerFound.isPresent()) {
				Customer updateCustomer = customerFound.get();
				updateCustomer.setAddress(newCustomer.getAddress());
				updateCustomer.setCity(newCustomer.getCity());
				updateCustomer.setCountry(newCustomer.getCountry());
				updateCustomer.setCreditLimit(newCustomer.getCreditLimit());
				updateCustomer.setFirstName(newCustomer.getFirstName());
				updateCustomer.setLastName(newCustomer.getLastName());
				updateCustomer.setPhoneNumber(newCustomer.getPhoneNumber());
				updateCustomer.setPostalCode(newCustomer.getPostalCode());
				updateCustomer.setSalesRepEmployeeNumber(newCustomer.getSalesRepEmployeeNumber());
				updateCustomer.setState(newCustomer.getState());
				updateCustomer.setUsername(newCustomer.getPhoneNumber());
				return new ResponseEntity<>(customerRepo.save(updateCustomer), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	Sửa role khách hàng
	@CrossOrigin
	@PutMapping("/roles/{roleId}/customers-set-role/{customerId}")
	public ResponseEntity<Object> updateRoleCustomer(@PathVariable long customerId,
			@PathVariable long roleId) {
		try {
			Optional<Customer> customerFound = customerRepo.findById(customerId);
			if (customerFound.isPresent()) {
				Customer updateCustomer = customerFound.get();
				List<Role> roleList = new ArrayList<>();
				roleList.add(roleRepo.findById(roleId).get());
				updateCustomer.setRoles(roleList);
				customerRepo.save(updateCustomer);
				return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	xóa tất cả khách hàng
	@DeleteMapping("/customers")
	public ResponseEntity<Object> deleteAllCustomer() {
		try {
			customerRepo.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	xóa khách hàng dựa trên id
	@DeleteMapping("/customers/{customerId}")
	public ResponseEntity<Object> deleteCustomerById(@PathVariable Long customerId) {
		try {
			Optional<Customer> customerFound = customerRepo.findById(customerId);
			if (customerFound.isPresent()) {
				customerRepo.deleteById(customerId);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
