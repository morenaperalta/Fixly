package com.femcoders.fixly.user.dtos;

public record UserSummaryResponse(
        Long id,
        String username,
        String firstName,
        String lastName
) {
}
