package com.devcamp.shop24h.service;

import com.devcamp.shop24h.model.*;
import com.devcamp.shop24h.security.UserPrincipal;

public interface UserService {
    Customer createUser(Customer user);

    UserPrincipal findByUsername(String username);
    UserPrincipal findByPhoneNumber(String phoneNUmber);
}
