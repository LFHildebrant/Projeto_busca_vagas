package com.utfpr.Projeto_Sistemas.dto.jobsearch;

import java.util.List;

public record JobSearchDto(
        List<FilterDto> filters
) {
}
