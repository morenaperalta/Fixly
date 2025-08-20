package com.femcoders.fixly.auth.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public record RegistrationRequest(
        @Schema(example = "johndoe")
        @NotBlank(message = "Username is required")
        @Size(max = 100, message = "Username must be a maximum of 100 characters long")
        String username,

        @Schema(example = "john.doe@email.com")
        @NotBlank(message = "Email is required")
        @Email(message = "Email not valid", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        String email,

        @Schema(example = "SecurePass123!")
        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must contain a minimum of 8 characters")
        @Pattern(message = "Password must contain a number, one uppercase letter, one lowercase letter and one special character", regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=.])(?=\\S+$).{8,}$")
        String password,

        @Schema(example = "John")
        @NotBlank(message = "Firstname is required")
        @Size(max = 100, message = "Firstname must be a maximum of 100 characters long")
        String firstName,

        @Schema(example = "Doe")
        @NotBlank(message = "Lastname is required")
        @Size(max = 100, message = "Lastname must be a maximum of 100 characters long")
        String lastName,

        @Schema(example = "Fixly Inc")
        @NotBlank(message = "Company is required")
        @Size(max = 100, message = "Company must be a maximum of 100 characters long")
        String company
) {
}
