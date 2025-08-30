package com.femcoders.fixly.user.dtos.response;

import com.femcoders.fixly.user.entities.User;
import com.femcoders.fixly.user.services.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class UserResponseFactory {
    private final UserAuthService userAuthService;

    public UserResponse createUserResponseByRole(User user, Authentication auth) {
        String role = userAuthService.extractRole(auth);

        return switch (role) {
            case "ROLE_ADMIN", "ROLE_SUPERVISOR" -> createAdminResponse(user);
            case "ROLE_TECHNICIAN", "ROLE_CLIENT" -> createUserSummaryResponse(user);
            default -> throw new IllegalArgumentException("Unknown role: " + role);
        };
    }

    private UserResponseForAdmin createAdminResponse(User user) {
        return new UserResponseForAdmin(user.getId(), user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getCompany(), user.getRole(), user.getCreatedAt(), user.getUpdatedAt());
    }

    private UserSummaryResponse createUserSummaryResponse(User user) {
        return new UserSummaryResponse(user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getCompany());
    }

    public List<UserResponse> createResponseList(List<User> users, Authentication auth) {
        return users.stream().map(user -> createUserResponseByRole(user, auth)).toList();
    }
}