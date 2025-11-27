package com.utfpr.Projeto_Sistemas.service;

import com.utfpr.Projeto_Sistemas.dto.job.CreateJobDto;
import com.utfpr.Projeto_Sistemas.entities.AreaJob;
import com.utfpr.Projeto_Sistemas.entities.Company;
import com.utfpr.Projeto_Sistemas.entities.Job;
import com.utfpr.Projeto_Sistemas.entities.Role;
import com.utfpr.Projeto_Sistemas.repository.CompanyRepository;
import com.utfpr.Projeto_Sistemas.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CompanyRepository companyRepository;

    public boolean createJob(CreateJobDto createJobDto, Long idCompany) {
        Company company = companyRepository.findByIdCompany(idCompany);
        var Entity = new Job(
                company,
                createJobDto.title(),
                AreaJob.from(createJobDto.area()),
                createJobDto.description(),
                createJobDto.state(),
                createJobDto.city(),
                verifyNullable(createJobDto.salary()),
                Instant.now(),
                null
        );
        Job savedJob = jobRepository.save(Entity);
        return jobRepository.existsById(savedJob.getIdJob());
    }
    public BigDecimal verifyNullable(BigDecimal value){
        if (value == null){
            return null;
        }
        return value;
    }
}
