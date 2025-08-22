package com.femcoders.fixly.auth;

import com.femcoders.fixly.auth.dtos.AuthMapper;
import com.femcoders.fixly.auth.dtos.JwtResponse;
import com.femcoders.fixly.auth.dtos.LoginRequest;
import com.femcoders.fixly.shared.exception.EntityAlreadyExistsException;
import com.femcoders.fixly.shared.security.CustomUserDetails;
import com.femcoders.fixly.shared.security.jwt.JwtService;
import com.femcoders.fixly.user.User;
import com.femcoders.fixly.user.UserRepository;
import com.femcoders.fixly.user.dtos.UserMapper;
import com.femcoders.fixly.auth.dtos.RegistrationRequest;
import com.femcoders.fixly.user.dtos.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserResponse register(RegistrationRequest request){
        if(userRepository.existsByUsername(request.username())){
            throw new EntityAlreadyExistsException(User.class.getSimpleName(), "username", request.username());
        }
        if(userRepository.existsByEmail(request.email())){
            throw new EntityAlreadyExistsException(User.class.getSimpleName(), "email", request.email());
        }
        User user = AuthMapper.registrationToEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        User registeredUser = userRepository.save(user);
        return UserMapper.userResponseToDto(registeredUser);
    }

    public JwtResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        CustomUserDetails userDetail = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetail);

        return new JwtResponse(token);
    }
}
