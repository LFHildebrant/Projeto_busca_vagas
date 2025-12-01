package com.utfpr.Projeto_Sistemas.dto.job;

import java.math.BigDecimal;

public record JobApplUser(
        Integer job_id ,

        String title,

        String area,

        String description,

        String company,

        String city,

        String state,

        String contact,

        BigDecimal salary,

        String feedback
) {
}
