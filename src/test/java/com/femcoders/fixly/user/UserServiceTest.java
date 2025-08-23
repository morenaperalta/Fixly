package com.femcoders.fixly.user;

import com.femcoders.fixly.shared.security.CustomUserDetails;
import com.femcoders.fixly.user.dtos.UserResponse;
import com.femcoders.fixly.user.dtos.UserResponseForAdmin;
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

    @InjectMocks
    private UserService userService;

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
        @DisplayName("When there is at least one user, it should return a list of UserResponseForAdmin")
        void getAllUsers_whenExistsUsers_returnListOfUserResponseForAdmin() {
            when(userRepository.findAll()).thenReturn(List.of(user1, user2));

            List<UserResponseForAdmin> result = userService.getAllUsers();

            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals(user1.getId(), result.get(0).id());
            assertEquals(user1.getUsername(), result.get(0).username());
            assertEquals(user1.getEmail(), result.get(0).email());
            assertEquals(user2.getId(), result.get(1).id());
            assertEquals(user2.getUsername(), result.get(1).username());
            verify(userRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("When there is at least one user, it should return a empty list")
        void getAllUsers_whenUsersNotExists_returnEmptyList() {
            when(userRepository.findAll()).thenReturn(Collections.emptyList());

            List<UserResponseForAdmin> result = userService.getAllUsers();

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
            Authentication authentication = mock(Authentication.class);
            SecurityContext securityContext = mock(SecurityContext.class);

            try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class)) {
                when(SecurityContextHolder.getContext()).thenReturn(securityContext);
                when(securityContext.getAuthentication()).thenReturn(authentication);
                when(authentication.getPrincipal()).thenReturn(userDetails);
                when(userRepository.findByUsername(user1.getUsername())).thenReturn(Optional.of(user1));

                UserResponse result = userService.getOwnProfile();

                assertNotNull(result);
                assertEquals(user1.getUsername(), result.username());
                assertEquals(user1.getEmail(), result.email());

                verify(userRepository, times(2)).findByUsername(user1.getUsername());
            }
        }
    }
}
