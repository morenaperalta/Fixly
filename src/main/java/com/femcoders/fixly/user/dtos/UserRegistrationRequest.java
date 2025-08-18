package com.femcoders.fixly.user.dtos;

import jakarta.validation.constraints.*;

public record UserRegistrationRequest(
        @NotBlank(message = "Username is required")
        @Size(max = 100, message = "Username must be a maximum of 100 characters long")
        String username,

        @NotBlank(message = "Email is required")
        @Email(message = "Email not valid", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must contain a minimum of 8 characters")
        @Pattern(message = "Password must contain a number, one uppercase letter, one lowercase letter and one special character", regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=.])(?=\\S+$).{8,}$")
        String password,

        @NotBlank(message = "Firstname is required")
        @Size(max = 100, message = "Firstname must be a maximum of 100 characters long")
        String firstName,

        @NotBlank(message = "Lastname is required")
        @Size(max = 100, message = "Lastname must be a maximum of 100 characters long")
        String lastName,

        @NotBlank(message = "Company is required")
        @Size(max = 100, message = "Company must be a maximum of 100 characters long")
        String company
) {
}
