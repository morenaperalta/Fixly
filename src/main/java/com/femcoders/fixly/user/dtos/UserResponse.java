package com.femcoders.fixly.user.dtos;

public sealed interface UserResponse permits UserSummaryResponse, UserResponseForAdmin {
    String username();
    String email();
    String firstName();
    String lastName();
    String company();
}
