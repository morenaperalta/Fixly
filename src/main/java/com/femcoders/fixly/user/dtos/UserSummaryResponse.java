package com.femcoders.fixly.user.dtos;

public record UserSummaryResponse(
        String username,
        String email,
        String firstName,
        String lastName,
        String company
) implements UserResponse {
}
