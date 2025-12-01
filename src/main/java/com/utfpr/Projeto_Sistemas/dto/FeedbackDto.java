package com.utfpr.Projeto_Sistemas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FeedbackDto (
        @NotNull(message = "user_id cannot be null")
        int user_id,
        @NotBlank(message = "message cannot be null")
        @Size(min = 10, max = 600, message = "message must be between 10 and 600 characters")
        String message
) {
}
