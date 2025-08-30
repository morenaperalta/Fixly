package com.femcoders.fixly.user.dtos.response;

public record UserSummaryResponse(
        String username,
        String firstName,
        String lastName,
        String email,
        String company
) implements UserResponse {
}
