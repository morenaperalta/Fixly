package com.femcoders.fixly.user.dtos;

import com.femcoders.fixly.user.Role;

import java.time.LocalDateTime;

public record AdminResponse(
        Long id,
        String username,
        String email,
        String firstName,
        String lastName,
        String company,
        Role role,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
