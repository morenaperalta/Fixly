package com.femcoders.fixly.auth;

import com.femcoders.fixly.auth.dtos.JwtResponse;
import com.femcoders.fixly.auth.dtos.LoginRequest;
import com.femcoders.fixly.auth.dtos.RegistrationRequest;
import com.femcoders.fixly.auth.services.AuthServiceImpl;
import com.femcoders.fixly.shared.exception.EntityAlreadyExistsException;
import com.femcoders.fixly.shared.security.CustomUserDetails;
import com.femcoders.fixly.shared.security.jwt.JwtService;
import com.femcoders.fixly.user.User;
import com.femcoders.fixly.user.UserRepository;
import com.femcoders.fixly.user.dtos.response.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for AuthService")
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;
    @Mock
    private Authentication authentication;
    @Mock
    private CustomUserDetails userDetails;

    @InjectMocks
    private AuthServiceImpl authService;

    private RegistrationRequest registrationRequest;
    private User savedUser;

    @BeforeEach
    void setUp() {
        registrationRequest = new RegistrationRequest("User1", "user1@email.com", "Password123!", "FirstName", "LastName", "Company");

        savedUser = new User(1L, "User1", "user1@email.com", "encodedPassword", "FirstName", "LastName", "Company", null, null, null);
    }

    @Nested
    @DisplayName("Register")
    class RegisterTests {
        @Test
        @DisplayName("Should register user correctly when request is valid")
        void register_whenCorrectRequest_returnsUserResponse() {

            when(passwordEncoder.encode(registrationRequest.password())).thenReturn("encodedPassword");
            when(userRepository.existsByUsername(registrationRequest.username())).thenReturn(false);
            when(userRepository.existsByEmail(registrationRequest.email())).thenReturn(false);

            when(userRepository.save(any(User.class))).thenReturn(savedUser);

            UserResponse response = authService.register(registrationRequest);

            assertNotNull(response);
            assertEquals(UserResponse.class, response.getClass());
            assertEquals("User1", response.username());
            assertEquals("user1@email.com", response.email());
            assertEquals("FirstName", response.firstName());
            assertEquals("LastName", response.lastName());
            assertEquals("Company", response.company());

            verify(userRepository, times(1)).existsByUsername(registrationRequest.username());
            verify(userRepository, times(1)).existsByEmail(registrationRequest.email());
            verify(passwordEncoder, times(1)).encode(registrationRequest.password());
            verify(userRepository, times(1)).save(any(User.class));
        }

        @Test
        @DisplayName("Should throw exception when username already exists")
        void register_whenUsernameExists_throwsException() {

            when(userRepository.existsByUsername(registrationRequest.username())).thenReturn(true);

            EntityAlreadyExistsException exception = assertThrows(EntityAlreadyExistsException.class, () -> authService.register(registrationRequest));

            assertEquals("User with username User1 already exists", exception.getMessage());

            verify(userRepository, never()).save(any());
        }

        @Test
        @DisplayName("Should throw exception when email already exists")
        void register_whenEmailExists_throwsException() {

            when(userRepository.existsByEmail(registrationRequest.email())).thenReturn(true);

            EntityAlreadyExistsException exception = assertThrows(EntityAlreadyExistsException.class, () -> authService.register(registrationRequest));

            assertEquals("User with email user1@email.com already exists", exception.getMessage());

            verify(userRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Login Tests")
    class LoginTests {
        @Test
        @DisplayName("Should return JWT token when login is successful")
        void login_whenValidCredentials_returnsJwtResponse() {
            LoginRequest loginRequest = new LoginRequest("User1", "Password123!");

            when(authenticationManager.authenticate(any())).thenReturn(authentication);
            when(authentication.getPrincipal()).thenReturn(userDetails);
            when(jwtService.generateToken(userDetails)).thenReturn("token");

            JwtResponse response = authService.login(loginRequest);

            assertNotNull(response);
            assertEquals("token", response.token());

            verify(authenticationManager).authenticate(any());
            verify(jwtService, times(1)).generateToken(userDetails);
        }

        @Test
        @DisplayName("Should throw BadCredentialsException when username is invalid")
        void login_whenInvalidUsername_throwsBadCredentialsException() {

            LoginRequest loginRequest = new LoginRequest("invalidUser", "Password123!");

            when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Invalid credentials"));

            BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> authService.login(loginRequest));

            assertEquals("Invalid credentials", exception.getMessage());

            verify(authenticationManager).authenticate(any());
            verify(jwtService, never()).generateToken(any());
        }

        @Test
        @DisplayName("Should throw BadCredentialsException when password is invalid")
        void login_whenInvalidPassword_throwsBadCredentialsException() {

            LoginRequest loginRequest = new LoginRequest("User1", "wrongPassword");

            when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Invalid credentials"));

            BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> authService.login(loginRequest));

            assertEquals("Invalid credentials", exception.getMessage());

            verify(authenticationManager).authenticate(any());
            verify(jwtService, never()).generateToken(any());
        }
    }
}
