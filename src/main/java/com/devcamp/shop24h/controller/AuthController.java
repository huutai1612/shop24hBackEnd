package com.devcamp.shop24h.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.PostMapping;
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
    
    
    private Long G_CUSTOMER_ID = 3L;
    private Long G_MANAGER_ID = 2L;

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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Customer user) {
        UserPrincipal userPrincipal = userService.findByPhoneNumber(user.getPhoneNumber());
        if (null == user || !new BCryptPasswordEncoder().matches(user.getPassword(), userPrincipal.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("tài khoản hoặc mật khẩu không chính xác");
        }
        Token token = new Token();
        token.setToken(jwtUtil.generateToken(userPrincipal));
        token.setTokenExpDate(jwtUtil.generateExpirationDate());
        token.setCreatedBy(userPrincipal.getUserId());
        tokenService.createToken(token);
        System.out.println(userPrincipal.getAuthorities());
        return ResponseEntity.ok(token.getToken());
    }
    
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("/user/checkrole")
    public ResponseEntity<Object> checkRole() {
    	try {
			return ResponseEntity.ok("customer");
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
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
}
