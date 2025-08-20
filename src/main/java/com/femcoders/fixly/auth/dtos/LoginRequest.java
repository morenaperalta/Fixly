package com.femcoders.fixly.auth.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequest (
        @Schema(example = "johndoe")
        @NotBlank(message = "Username is required")
        @Size(max = 100, message = "Username must be a maximum of 100 characters long")
        String username,

        @Schema(example = "SecurePass123!")
        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must contain a minimum of 8 characters")
        @Pattern(message = "Password must contain a number, one uppercase letter, one lowercase letter and one special character", regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=.])(?=\\S+$).{8,}$")
        String password
) {
}
