package com.femcoders.fixly.Auth;

import com.femcoders.fixly.auth.AuthService;
import com.femcoders.fixly.auth.dtos.RegistrationRequest;
import com.femcoders.fixly.exception.EntityAlreadyExistsException;
import com.femcoders.fixly.security.jwt.JwtService;
import com.femcoders.fixly.user.User;
import com.femcoders.fixly.user.UserRepository;
import com.femcoders.fixly.user.dtos.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
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

    @InjectMocks
    private AuthService authService;

    private RegistrationRequest registrationRequest;

    @BeforeEach
    void setUp() {
        registrationRequest = new RegistrationRequest(
                "User1",
                "user1@email.com",
                "Password123!",
                "FirstName",
                "LastName",
                "Company"
        );
    }

    @Nested
    @DisplayName("Register")
    class RegisterTests{
        @Test
        @DisplayName("Should register user correctly when request is valid")
        void register_whenCorrectRequest_returnsUserResponse(){

            when(passwordEncoder.encode(registrationRequest.password())).thenReturn("encodedPassword");
            when(userRepository.existsByUsername(registrationRequest.username())).thenReturn(false);
            when(userRepository.existsByEmail(registrationRequest.email())).thenReturn(false);

            User savedUser = new User(1L, registrationRequest.username(), registrationRequest.email(), "encodedPassword", registrationRequest.firstName(), registrationRequest.lastName(), registrationRequest.company(), null, null, null);

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
            verify(userRepository, times(1)).save(any(User.class));
            verify(passwordEncoder, times(1)).encode(registrationRequest.password());
        }

        @Test
        @DisplayName("Should throw exception when username already exists")
        void register_whenUsernameExists_throwsException() {

            when(userRepository.existsByUsername(registrationRequest.username())).thenReturn(true);

            EntityAlreadyExistsException exception = assertThrows(EntityAlreadyExistsException.class, () -> authService.register(registrationRequest));

            assertEquals("User with username User1 already exists", exception.getMessage());
        }
    }
}
