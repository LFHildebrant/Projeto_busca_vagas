package com.utfpr.Projeto_Sistemas.dto.job;

import com.utfpr.Projeto_Sistemas.entities.Job;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record JobDto(
        Integer job_id ,

        String title,

        String area,

        String description,

        String company,

        String city,

        String state,

        String contact,

        BigDecimal salary
) {
    public JobDto(Job job) {
        this(
                job.getIdJob(),
                job.getTitle(),
                job.getArea().getAreaJob(),
                job.getDescription(),
                job.getCompany().getName(),
                job.getCity(),
                job.getState(),
                job.getCompany().getEmail(),
                job.getSalary()

        );
    }
}
