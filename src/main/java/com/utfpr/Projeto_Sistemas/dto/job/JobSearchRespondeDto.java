package com.utfpr.Projeto_Sistemas.dto.job;

public record JobSearchRespondeDto(
        int job_id,
        String title,
        String area,
        String company,
        String des
) {
}
