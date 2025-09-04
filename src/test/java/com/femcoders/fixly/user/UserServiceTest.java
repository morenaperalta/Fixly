package com.femcoders.fixly.user;

import com.femcoders.fixly.shared.security.CustomUserDetails;
import com.femcoders.fixly.user.dtos.UserMapper;
import com.femcoders.fixly.user.dtos.response.UserResponse;
import com.femcoders.fixly.user.entities.Role;
import com.femcoders.fixly.user.entities.User;
import com.femcoders.fixly.user.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for UserService")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user1;
    private User user2;


    @BeforeEach
    void setUp() {
        user1 = new User(1L, "User1", "user1@email.com", "password123", "FirstName", "LastName", "Company", Role.CLIENT, LocalDateTime.now(), LocalDateTime.now());
        user2 = new User(2L, "User2", "user2@email.com", "password456", "SecondName", "SecondLast", "Company2", Role.ADMIN, LocalDateTime.now(), LocalDateTime.now());
    }


    @Nested
    @DisplayName("Get all users")
    class GetAllUsersTests {
        @Test
        @DisplayName("When there is at least one user, it should return a list of UserResponse")
        void getAllUsers_whenExistsUsers_returnListOfUserResponse() {
            when(userRepository.findAll()).thenReturn(List.of(user1, user2));

            List<UserResponse> result = userService.getAllUsers(authentication);

            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals(user1.getUsername(), result.get(0).username());
            assertEquals(user1.getEmail(), result.get(0).email());
            assertEquals(user2.getUsername(), result.get(1).username());
            verify(userRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("When there is at least one user, it should return a empty list")
        void getAllUsers_whenUsersNotExists_returnEmptyList() {
            when(userRepository.findAll()).thenReturn(Collections.emptyList());

            List<UserResponse> result = userService.getAllUsers(authentication);

            assertNotNull(result);
            assertTrue(result.isEmpty());
            assertEquals(0, result.size());
            verify(userRepository, times(1)).findAll();
        }
    }

    @Nested
    @DisplayName("Get own profile")
    class GetOwnProfileTests {
        @Test
        @DisplayName("When authenticated user exists, it should return UserResponse")
        void getOwnProfile_whenAuthenticatedUserExists_returnUserResponse() {
            CustomUserDetails userDetails = new CustomUserDetails(user1);

            try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class)) {
                when(SecurityContextHolder.getContext()).thenReturn(securityContext);
                when(securityContext.getAuthentication()).thenReturn(authentication);
                when(authentication.getPrincipal()).thenReturn(userDetails);
                when(userRepository.findByUsername(user1.getUsername())).thenReturn(Optional.of(user1));

                UserResponse result = userService.getOwnProfile(authentication);

                assertNotNull(result);
                assertEquals(user1.getUsername(), result.username());
                assertEquals(user1.getEmail(), result.email());

                verify(userMapper, times(2)).createUserResponseByRole(user1,authentication);
            }
        }
    }
}
