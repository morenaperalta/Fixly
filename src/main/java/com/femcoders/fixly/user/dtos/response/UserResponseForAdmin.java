package com.femcoders.fixly.user.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.femcoders.fixly.user.Role;

import java.time.LocalDateTime;

public record UserResponseForAdmin(
        Long id,
        String username,
        String email,
        String firstName,
        String lastName,
        String company,
        Role role,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt
) implements UserResponse {
}
