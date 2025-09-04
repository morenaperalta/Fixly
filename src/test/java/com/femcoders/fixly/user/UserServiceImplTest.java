package com.femcoders.fixly.user;

import com.femcoders.fixly.user.dtos.UserMapper;
import com.femcoders.fixly.user.dtos.response.UserResponse;
import com.femcoders.fixly.user.dtos.response.UserResponseForAdmin;
import com.femcoders.fixly.user.dtos.response.UserSummaryResponse;
import com.femcoders.fixly.user.entities.Role;
import com.femcoders.fixly.user.entities.User;
import com.femcoders.fixly.user.services.UserAuthService;
import com.femcoders.fixly.user.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for UserService")
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserAuthService userAuthService;

    @InjectMocks
    private UserServiceImpl userService;

    private User user1;
    private User user2;
    private UserResponse userResponse1;
    private UserResponse userResponse2;


    @BeforeEach
    void setUp() {
        user1 = new User(1L, "User1", "user1@email.com", "password123", "FirstName", "LastName", "Company", Role.CLIENT, LocalDateTime.now(), LocalDateTime.now());
        user2 = new User(2L, "User2", "user2@email.com", "password456", "SecondName", "SecondLast", "Company2", Role.ADMIN, LocalDateTime.now(), LocalDateTime.now());
        userResponse1 = new UserSummaryResponse(user1.getUsername(), user1.getFirstName(), user1.getLastName(), user1.getEmail(), user1.getCompany());
        userResponse2 = new UserResponseForAdmin(user2.getId(), user2.getUsername(), user2.getEmail(), user2.getFirstName(), user2.getLastName(), user2.getCompany(), user2.getRole(), user2.getCreatedAt(), user2.getUpdatedAt());
    }


    @Nested
    @DisplayName("Get all users")
    class GetAllUsersTests {
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

        @Test
        @DisplayName("When there are users, should return a list of UserResponse")
        void getAllUsers_whenUsersExist_returnListOfUserResponse() {
            when(userRepository.findAll()).thenReturn(List.of(user1, user2));
            when(userMapper.createResponseList(List.of(user1, user2), authentication)).thenReturn(List.of(userResponse1, userResponse2));

            List<UserResponse> result = userService.getAllUsers(authentication);

            assertNotNull(result);
            assertEquals(2, result.size());
            assertTrue(result.containsAll(List.of(userResponse1, userResponse2)));

            verify(userRepository, times(1)).findAll();
            verify(userMapper, times(1)).createResponseList(List.of(user1, user2), authentication);
            verifyNoMoreInteractions(userMapper);
        }


    }

    @Nested
    @DisplayName("Get own profile")
    class GetOwnProfileTests {
        @Test
        @DisplayName("When authenticated user exists, it should return the correct UserResponse based on role")
        void getOwnProfile_whenAuthenticatedUserExists_returnUserResponse() {
            UserResponse expectedResponse = new UserSummaryResponse(user1.getUsername(), user1.getFirstName(), user1.getLastName(), user1.getEmail(), user1.getCompany());

            when(userAuthService.getAuthenticatedUser()).thenReturn(user1);
            when(userMapper.createUserResponseByRole(user1, authentication)).thenReturn(expectedResponse);


            UserResponse result = userService.getOwnProfile(authentication);

            assertNotNull(result);
            assertEquals(expectedResponse, result);
            assertInstanceOf(UserSummaryResponse.class, result);

            verify(userAuthService, times(1)).getAuthenticatedUser();
            verify(userMapper, times(1)).createUserResponseByRole(user1, authentication);
        }

        @Test
        @DisplayName("When authenticated user (as ADMIN) exists, it should return the correct UserResponse")
        void getOwnProfile_whenAuthenticatedUserIsAdmin_returnAdminUserResponse() {

            UserResponse expectedResponse = new UserResponseForAdmin(user2.getId(), user2.getUsername(), user2.getEmail(), user2.getFirstName(), user2.getLastName(), user2.getCompany(), user2.getRole(), user2.getCreatedAt(), user2.getUpdatedAt());

            when(userAuthService.getAuthenticatedUser()).thenReturn(user2);
            when(userMapper.createUserResponseByRole(user2, authentication)).thenReturn(expectedResponse);

            UserResponse result = userService.getOwnProfile(authentication);

            assertNotNull(result);
            assertEquals(expectedResponse, result);
            assertInstanceOf(UserResponseForAdmin.class, result);

            verify(userAuthService, times(1)).getAuthenticatedUser();
            verify(userMapper, times(1)).createUserResponseByRole(user2, authentication);
        }

        @Test
        @DisplayName("When authenticated (as CLIENT) user exists, should return the correct UserSummaryResponse")
        void getOwnProfile_whenAuthenticatedUserIsClient_returnUserSummaryResponse() {
            when(userAuthService.getAuthenticatedUser()).thenReturn(user1);
            when(userMapper.createUserResponseByRole(user1, authentication)).thenReturn(userResponse1);

            UserResponse result = userService.getOwnProfile(authentication);

            assertNotNull(result);
            assertInstanceOf(UserSummaryResponse.class, result, "Should return UserSummaryResponse for CLIENT");
            assertEquals(userResponse1, result, "Should return the correct UserSummaryResponse");

            verify(userAuthService, times(1)).getAuthenticatedUser();
            verify(userMapper, times(1)).createUserResponseByRole(user1, authentication);
        }
    }
}
