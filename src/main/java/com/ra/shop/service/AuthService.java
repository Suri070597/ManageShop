package com.ra.shop.service;

import com.ra.shop.model.dto.JwtResponse;
import com.ra.shop.model.dto.LoginRequest;
import com.ra.shop.model.dto.RegisterRequest;

public interface AuthService {
    JwtResponse login(LoginRequest loginRequest);

    String register(RegisterRequest registerRequest);
}
