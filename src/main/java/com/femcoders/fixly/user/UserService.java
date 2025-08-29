package com.femcoders.fixly.user;

import com.femcoders.fixly.shared.exception.EntityNotFoundException;
import com.femcoders.fixly.shared.security.CustomUserDetails;
import com.femcoders.fixly.user.dtos.UserMapper;
import com.femcoders.fixly.user.dtos.UserResponse;
import com.femcoders.fixly.user.dtos.UserResponseFactory;
import com.femcoders.fixly.user.dtos.UserResponseForAdmin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private static final String USERNAME_FIELD = "username";
    private final UserRepository userRepository;
    private final UserResponseFactory userResponseFactory;

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers(Authentication auth) {
        List<User> users = userRepository.findAll();
        return userResponseFactory.createResponseList(users, auth);
    }

    @Transactional(readOnly = true)
    public UserResponse getOwnProfile(Authentication auth) {
        User user = getAuthenticatedUser();
        return userResponseFactory.createUserResponseByRole(user, auth);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws EntityNotFoundException {
        return userRepository.findByUsername(username).map(CustomUserDetails::new).orElseThrow(() -> new EntityNotFoundException(User.class.getSimpleName(), USERNAME_FIELD, username));
    }

    public User getAuthenticatedUser() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = userDetails.getUsername();

        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(User.class.getSimpleName(), USERNAME_FIELD, username));
    }
}