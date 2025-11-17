package com.utfpr.Projeto_Sistemas.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateUserDto(
        @Pattern(regexp = "^([0-9a-zA-Z]{3,20})?$", message = "Invalid password format")
        String password,
        @Pattern(regexp = "^$|^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "invalid email format")
        String email,
        @NotBlank(message = "Name can not be empty")
        @Size(min = 4, max = 150, message = "name must be between 4 and 150 characters")
        String name,
        @Pattern(regexp = "(?s)^$|^.{10,600}$", message = "experience must be between 10 and 600 characters")
        String experience,
        @Pattern(regexp = "^$|^(\\([0-9]{2}\\)|[0-9]{2})9?[0-9]{4}-?[0-9]{4}$", message = "invalid phone format")
        String phone,
        @Pattern(regexp = "(?s)^$|^.{10,600}$", message = "education must be between 10 and 600 characters")
        String education

) {
}
