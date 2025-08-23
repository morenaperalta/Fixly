package com.femcoders.fixly.user;

import com.femcoders.fixly.user.dtos.UserResponseForAdmin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    void setUp(){
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
    }
}
