package com.femcoders.fixly.auth;

import com.femcoders.fixly.exception.EntityAlreadyExistsException;
import com.femcoders.fixly.user.User;
import com.femcoders.fixly.user.UserRepository;
import com.femcoders.fixly.user.dtos.UserMapper;
import com.femcoders.fixly.user.dtos.UserRegistrationRequest;
import com.femcoders.fixly.user.dtos.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(UserRegistrationRequest request){
        if(Boolean.TRUE.equals(userRepository.existsByUsername(request.username()))){
            throw new EntityAlreadyExistsException(User.class.getSimpleName(), "username", request.username());
        }
        if(Boolean.TRUE.equals(userRepository.existsByEmail(request.email()))){
            throw new EntityAlreadyExistsException(User.class.getSimpleName(), "email", request.email());
        }
        User user = UserMapper.userRegistrationToEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        User registeredUser = userRepository.save(user);
        return UserMapper.userResponseToDto(registeredUser);
    }
}
