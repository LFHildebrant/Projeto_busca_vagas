package com.utfpr.Projeto_Sistemas.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateCompanyDto(String username,
                               @NotBlank(message = "Password cannot be empty")
                               @Pattern(regexp = "^[0-9a-zA-Z]{3,20}$", message = "Invalid password format")
                               String password, String email, String name, String business, String phone, String street, String city, String state, String number) {
}
