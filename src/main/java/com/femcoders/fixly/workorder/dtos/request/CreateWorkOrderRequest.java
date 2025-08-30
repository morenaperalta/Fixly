package com.femcoders.fixly.workorder.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateWorkOrderRequest(
        @NotBlank(message = "Title must not be blank")
        @Size(min = 5)
        String title,

        @NotBlank(message = "Description must not be blank")
        String description,

        @NotBlank(message = "Location must not be blank")
        @Size(min = 3)
        String location) {
}
