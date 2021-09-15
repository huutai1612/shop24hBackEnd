package com.devcamp.shop24h.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devcamp.shop24h.model.*;
import com.devcamp.shop24h.repository.CustomerRepo;
import com.devcamp.shop24h.security.UserPrincipal;
import com.devcamp.shop24h.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private CustomerRepo userRepository;

    @Override
    public Customer createUser(Customer user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public UserPrincipal findByPhoneNumber(String phoneNumber) {
        Customer user = userRepository.findByPhoneNumber(phoneNumber);
        UserPrincipal userPrincipal = new UserPrincipal();
        if (null != user) {
            Set<String> authorities = new HashSet<>();
            if (null != user.getRoles()) user.getRoles().forEach(r -> {
                authorities.add(r.getRoleKey());
                r.getPermissions().forEach(p -> authorities.add(p.getPermissionKey()));
            });

            userPrincipal.setUserId(user.getId());
            userPrincipal.setUsername(user.getPhoneNumber());
            userPrincipal.setPassword(user.getPassword());
            userPrincipal.setAuthorities(authorities);
        }
        return userPrincipal;
    }

	@Override
	public UserPrincipal findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
