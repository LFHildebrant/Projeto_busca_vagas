package com.utfpr.Projeto_Sistemas.dto.job;

import com.utfpr.Projeto_Sistemas.entities.Job;

import java.util.List;

public record JobsSearchDto(
        List<JobDto> items
) {

}
