package com.utfpr.Projeto_Sistemas.dto.jobsearch;

import java.math.BigDecimal;

public record SalaryRangeDto(
        BigDecimal min,
        BigDecimal max
) {
}
