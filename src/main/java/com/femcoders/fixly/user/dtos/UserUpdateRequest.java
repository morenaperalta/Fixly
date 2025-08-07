package com.femcoders.fixly.user.dtos;

import jakarta.validation.constraints.*;

public record UserUpdateRequest(
        @Size(max = 100, message = "Username must be a maximum of 100 characters long")
        String username,

        @Email(message = "Email not valid", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        String email,

        @Size(max = 100, message = "Firstname must be a maximum of 100 characters long")
        String firstName,

        @Size(max = 100, message = "Lastname must be a maximum of 100 characters long")
        String lastName,

        @Size(max = 100, message = "Company must be a maximum of 100 characters long")
        String company
) {
}
