package com.utfpr.Projeto_Sistemas.dto.job;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ApplicationDto(
        @NotBlank
        String name,

        String email,

        String  phone,

        @NotBlank
        @Size(max = 600, message = "maximum size of 600 characters")
        String education,

        @NotBlank
        @Size(max = 600, message = "maximum size of 600 characters")
        String experience

) {
}
