package com.utfpr.Projeto_Sistemas.service;

import com.utfpr.Projeto_Sistemas.dto.company.CompanyDto;
import com.utfpr.Projeto_Sistemas.dto.company.UpdateCompanyDto;
import com.utfpr.Projeto_Sistemas.dto.job.CreateJobDto;
import com.utfpr.Projeto_Sistemas.dto.job.JobDto;
import com.utfpr.Projeto_Sistemas.dto.job.UpdateJobDto;
import com.utfpr.Projeto_Sistemas.dto.jobsearch.FilterDto;
import com.utfpr.Projeto_Sistemas.entities.*;
import com.utfpr.Projeto_Sistemas.repository.CompanyRepository;
import com.utfpr.Projeto_Sistemas.repository.JobRepository;
import com.utfpr.Projeto_Sistemas.repository.JobSpecifications;
import com.utfpr.Projeto_Sistemas.utilities.ApiResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
    public JobDto getDataJob(long idJob){
        Job job = (Job) jobRepository.findByIdJob(idJob);
        if (job == null){return null;}
        return new JobDto(job.getIdJob(), job.getTitle(), job.getArea().getAreaJob(), job.getDescription(), job.getCompany().getName(), job.getCity(), job.getState(), job.getCompany().getEmail(), job.getSalary());
    }
    public ResponseEntity<?>  updateJob(UpdateJobDto updateJobDto, long idCompany, long idJob) {
        Job existingJob = (Job) jobRepository.findByIdJob(idJob);
        if (existingJob == null){
            return ResponseEntity.status(404).body(new ApiResponse("Job not found"));}

        if (existingJob.getCompany().getIdCompany() == idCompany){
            existingJob.setTitle(updateJobDto.title());
            existingJob.setArea(AreaJob.from(updateJobDto.area()));
            existingJob.setDescription(updateJobDto.description());
            existingJob.setState(updateJobDto.state());
            existingJob.setCity(updateJobDto.city());
            existingJob.setSalary(verifyNullable(updateJobDto.salary()));

            Job savedJob = jobRepository.save(existingJob);
            if (jobRepository.existsById(savedJob.getIdJob())){
                return ResponseEntity.status(200).body(new ApiResponse("Job updated"));
            }
        }
        return ResponseEntity.status(403).body(new ApiResponse("Forbidden"));
        
    }
    @Transactional
    public ResponseEntity<?> deleteJob(long idCompany, long idJob){
        Job existingJob = (Job) jobRepository.findByIdJob(idJob);
        if (existingJob == null){
            return ResponseEntity.status(404).body(new ApiResponse("Job not found"));}

        if (existingJob.getCompany().getIdCompany() == idCompany) {
            Integer result = jobRepository.deleteJobByIdJob(Math.toIntExact(idJob));
            companyRepository.flush(); // sync db
            if (result > 0){
                return ResponseEntity.status(200).body(new ApiResponse("Job deleted"));
            }
        }
        return ResponseEntity.status(403).body(new ApiResponse("Forbidden"));
    }
    public List<Job> searchJobsWithSpecs(FilterDto filterDto){
        List<Specification<Job>> specifications = new ArrayList<>();
        if (filterDto.title() != null && !filterDto.title().isEmpty()){
            specifications.add(JobSpecifications.likeTitle(filterDto.title()));
        }
        if (filterDto.area() != null && !filterDto.area().isEmpty()){
            specifications.add(JobSpecifications.likeArea(filterDto.area()));
        }
        if (filterDto.company() != null && !filterDto.company().isEmpty()){
            specifications.add(JobSpecifications.likeCompany(filterDto.company()));
        }
        if (filterDto.state() != null && !filterDto.state().isEmpty()){
            specifications.add(JobSpecifications.likeState(filterDto.state()));
        }
        if (filterDto.city() != null && !filterDto.city().isEmpty()){
            specifications.add(JobSpecifications.likeCity(filterDto.city()));
        }
        if (filterDto.salary_range() != null && filterDto.salary_range().max() != null){
            specifications.add(JobSpecifications.salaryMax(filterDto.salary_range().max()));
        }
        if (filterDto.salary_range() != null && filterDto.salary_range().min() != null){
            specifications.add(JobSpecifications.salaryMin(filterDto.salary_range().min()));
        }
        Specification<Job> finalSpec = Specification.allOf(specifications);
        return jobRepository.findAll(finalSpec);
    }

    public BigDecimal verifyNullable(BigDecimal value){
        if (value == null){
            return null;
        }
        return value;
    }
}
