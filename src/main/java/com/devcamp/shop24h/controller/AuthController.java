package com.devcamp.shop24h.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.devcamp.shop24h.model.*;
import com.devcamp.shop24h.repository.CustomerRepo;
import com.devcamp.shop24h.repository.RoleRepo;
import com.devcamp.shop24h.security.JwtUtil;
import com.devcamp.shop24h.security.UserPrincipal;
import com.devcamp.shop24h.service.TokenService;
import com.devcamp.shop24h.service.UserService;

@RestController
@CrossOrigin
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private RoleRepo roleRepo;
    
    @Autowired
    private CustomerRepo customerRepo;
    
    private Long G_ADMIN_ID = 1L;
    private Long G_CUSTOMER_ID = 3L;
    private Long G_MANAGER_ID = 2L;

//    register customer
    @PostMapping("/register/customer")
    public ResponseEntity<Object> registerCUstomer(@RequestBody Customer user) {
    	try {
    		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
    		user.setUsername(user.getPhoneNumber());
    		user.setRoles(Arrays.asList(roleRepo.findById(G_CUSTOMER_ID).get()));
    		return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
//    register manager
    @PostMapping("/register/manager")
    public ResponseEntity<Object> registerManager(@RequestBody Customer user) {
    	try {
    		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
    		user.setUsername(user.getPhoneNumber());
    		user.setRoles(Arrays.asList(roleRepo.findById(G_MANAGER_ID).get()));
    		return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
//  register Admin
  @PostMapping("/register/admin")
  public ResponseEntity<Object> regisTerAdmin(@RequestBody Customer user) {
  	try {
  		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
  		user.setUsername(user.getPhoneNumber());
  		user.setRoles(Arrays.asList(roleRepo.findById(G_ADMIN_ID ).get()));
  		return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
  }

//    login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Customer user) {
        UserPrincipal userPrincipal = userService.findByPhoneNumber(user.getPhoneNumber());
        if (null == user || !new BCryptPasswordEncoder().matches(user.getPassword(), userPrincipal.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("t??i kho???n ho???c m???t kh???u kh??ng ch??nh x??c");
        }
        Token token = new Token();
        token.setToken(jwtUtil.generateToken(userPrincipal));
        token.setTokenExpDate(jwtUtil.generateExpirationDate());
        token.setCreatedBy(userPrincipal.getUserId());
        tokenService.createToken(token);
        System.out.println(userPrincipal.getAuthorities());
        return ResponseEntity.ok(token.getToken());
    }
    
//    change password by admin
    @PutMapping("/admin/change-password/customers/{customerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> adminChangePassWord(@PathVariable long customerId
    		,@RequestBody Customer newCustomer) {
    	try {
    		Optional<Customer> customerFound = customerRepo.findById(customerId);
    		if (customerFound.isPresent()) {
    			Customer updateCustomer = customerFound.get();
    			updateCustomer.setPassword(new BCryptPasswordEncoder().encode(newCustomer.getPassword()));
    			customerRepo.save(updateCustomer);
    			return new ResponseEntity<>("Password changed", HttpStatus.OK);
    		} else {
    			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    		}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
//    change password by user
    @PreAuthorize("hasAuthority('UPDATE')")
    @PutMapping("/customer/change-password/customers/{customerId}/old-password/{oldPassword}")
    public ResponseEntity<Object> adminChangePassWord(@PathVariable long customerId
    		,@PathVariable String oldPassword
    		,@RequestBody Customer newCustomer) {
    	try {
    		Optional<Customer> customerFound = customerRepo.findById(customerId);
    		if (customerFound.isPresent()) {
    			Customer updateCustomer = customerFound.get();
    			String currentPas = updateCustomer.getPassword();
    			if (!new BCryptPasswordEncoder().matches(oldPassword, currentPas)) {
    				return new ResponseEntity<>("Password not match", HttpStatus.UNAUTHORIZED);
    			} else {
    				updateCustomer.setPassword(new BCryptPasswordEncoder().encode(newCustomer.getPassword()));
    				customerRepo.save(updateCustomer);
    				return new ResponseEntity<>("Password's changed", HttpStatus.OK);
    			}	
    		} else {
    			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    		}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
}
