package com.femcoders.fixly.user.dtos;

public record UserResponse(
        String username,
        String email,
        String firstName,
        String lastName,
        String company
) {
}
