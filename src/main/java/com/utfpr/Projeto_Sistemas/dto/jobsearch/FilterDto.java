package com.utfpr.Projeto_Sistemas.dto.jobsearch;

public record FilterDto(
        String title,
        String area,
        String company,
        String state,
        String city,
        SalaryRangeDto salary_range
) {
}
