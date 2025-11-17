package com.utfpr.Projeto_Sistemas.dto.company;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

public record UpdateCompanyDto(
        //@NotEmpty(message = "Password cannot be empty")
        @Pattern(regexp = "^([0-9a-zA-Z]{3,20})?$", message = "Invalid password format")
        String password,
        @Column(name = "email", nullable = true)
        @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "invalid email format")
        String email,
        @NotBlank(message = "Name can not be empty")
        @Size(min = 4, max = 150, message = "name must be between 4 and 150 characters")
        String name,
        @NotBlank(message = "business can not be empty")
        @Size(min = 4, max = 150, message = "business must be between 4 and 150 characters")
        String business,
        @Pattern(regexp = "^$|^(\\([0-9]{2}\\)|[0-9]{2})9?[0-9]{4}-?[0-9]{4}$", message = "invalid phone format")
        String phone,
        @NotBlank(message = "street can not be empty")
        @Size(min = 3, max = 150, message = "street must be between 3 and 150 characters")
        String street,
        @NotBlank(message = "city can not be empty")
        @Size(min = 3, max = 150, message = "city must be between 3 and 150 characters")
        String city,
        @NotBlank(message = "state cannot be empty")
        @Pattern(regexp = "^(?:AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO)$", message = "invalid state format, must be XX")
        String state,
        @NotBlank(message = "number can not be null")
        @Pattern(regexp = "^[0-9]{1,8}$", message = "invalid number format. Must be a positive number")
        String number
) {
}
