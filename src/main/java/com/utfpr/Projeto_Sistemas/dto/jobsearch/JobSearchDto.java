package com.utfpr.Projeto_Sistemas.dto.jobsearch;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record JobSearchDto(
        @NotNull
        List<FilterDto> filters
) {
}
