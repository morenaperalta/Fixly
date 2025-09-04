package com.femcoders.fixly.user.services;

import com.femcoders.fixly.user.dtos.UserMapper;
import com.femcoders.fixly.user.entities.User;
import com.femcoders.fixly.user.UserRepository;
import com.femcoders.fixly.user.dtos.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private static final String USERNAME_FIELD = "username";
    private final UserRepository userRepository;
    private final UserAuthService userAuthService;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    @Override
    public List<UserResponse> getAllUsers(Authentication auth) {
        List<User> users = userRepository.findAll();
        return userMapper.createResponseList(users, auth);
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponse getOwnProfile(Authentication auth) {
        User user = userAuthService.getAuthenticatedUser();
        return userMapper.createUserResponseByRole(user, auth);
    }

}