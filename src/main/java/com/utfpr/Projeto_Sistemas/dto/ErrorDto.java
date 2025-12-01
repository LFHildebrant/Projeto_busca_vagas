package com.utfpr.Projeto_Sistemas.dto;

import jakarta.validation.constraints.NotBlank;

public record ErrorDto(

        @NotBlank
        String message
) {
}
