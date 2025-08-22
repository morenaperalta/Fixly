package com.femcoders.fixly.workorder.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateWorkOrderRequest(
        @NotBlank
        @Size(min = 5)
        String title,

        @NotBlank
        String description,

        @NotBlank
        @Size(min = 3)
        String location) {
}
