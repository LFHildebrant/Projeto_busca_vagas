package com.utfpr.Projeto_Sistemas.dto.job;

import com.utfpr.Projeto_Sistemas.entities.AreaJob;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateJobDto(

        @NotBlank(message = "Title can not be empty")
        @Size(min = 3, max = 150)
        String title,

        @NotNull(message = "Area can not be empty")
        String area,

        @NotBlank(message = "Description can not be empty")
        @Size(min = 10, max = 5000)
        String description,

        @NotBlank(message = "State can not be empty")
        @Pattern(regexp = "^(?:AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO)$", message = "invalid state format, must be XX")
        String state,

        @NotBlank(message = "City can not be empty")
        String city,

        @Positive(message = "Salary must be positive")
        BigDecimal salary
) {
}
