package com.femcoders.fixly.auth.services;

import com.femcoders.fixly.auth.dtos.JwtResponse;
import com.femcoders.fixly.auth.dtos.LoginRequest;
import com.femcoders.fixly.auth.dtos.RegistrationRequest;
import com.femcoders.fixly.user.dtos.response.UserResponse;

public interface AuthService {
    UserResponse register(RegistrationRequest request);
    JwtResponse login(LoginRequest request);
}
