package com.femcoders.fixly.user.services;

import com.femcoders.fixly.user.dtos.response.UserResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers(Authentication auth);
    UserResponse getOwnProfile(Authentication auth);
}
