package com.utfpr.Projeto_Sistemas.controller;

import com.utfpr.Projeto_Sistemas.config.TokenService;
import com.utfpr.Projeto_Sistemas.config.TokenWhitelist;
import com.utfpr.Projeto_Sistemas.dto.company.UpdateCompanyDto;
import com.utfpr.Projeto_Sistemas.dto.job.*;
import com.utfpr.Projeto_Sistemas.dto.jobsearch.JobSearchDto;
import com.utfpr.Projeto_Sistemas.entities.Job;
import com.utfpr.Projeto_Sistemas.service.CompanyService;
import com.utfpr.Projeto_Sistemas.service.JobService;
import com.utfpr.Projeto_Sistemas.service.UserService;
import com.utfpr.Projeto_Sistemas.utilities.ApiResponse;
import com.utfpr.Projeto_Sistemas.utilities.VerificarionMethods;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;
    private final TokenService tokenService;
    private final VerificarionMethods  verificarionMethods;

    public JobController(JobService jobService, TokenService tokenService,  VerificarionMethods verificarionMethods) {
        this.jobService = jobService;
        this.tokenService = tokenService;
        this.verificarionMethods = verificarionMethods;
    }

    @PostMapping
    public ResponseEntity<?> createJob(@RequestHeader("Authorization") String tokenHeader ,@RequestBody @Valid CreateJobDto createJobDto) {
        String tokenCleaned = tokenService.replaceToken(tokenHeader);
        long idCompany = Long.parseLong(tokenService.validateToken(tokenCleaned));
        ResponseEntity response = verificarionMethods.verifyTokenInvalidUsernotFound(tokenHeader);
        if (response!=null){
            return response;
        }
        boolean created = jobService.createJob(createJobDto, idCompany);
        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Created"));
        } else {
            return ResponseEntity.status(500).body("Error while saving: ");
        }
    }
    @GetMapping("/{job_id}")
    public ResponseEntity<?> getJob(@RequestHeader ("Authorization") String tokenHeader, @PathVariable int job_id){
        ResponseEntity<?> response = null;
        response = verificarionMethods.verifyTokenInvalidUsernotFound(tokenHeader);
        if (response!=null){
            return response;
        }
        /*String tokenCleaned = tokenService.replaceToken(tokenHeader);
        long idCompany = Long.parseLong(tokenService.validateToken(tokenCleaned));*/
        JobDto jobDto = jobService.getDataJob(job_id);
        if (jobDto==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Job not found"));
        }
        return ResponseEntity.status(200).body(jobDto);
    }
    @PatchMapping("/{job_id}")
    public ResponseEntity<?> updateJob (@RequestHeader ("Authorization") String tokenHeader, @RequestBody  @Valid UpdateJobDto updateJobDto, @PathVariable int job_id){
        ResponseEntity<?> response = null;
        response = verificarionMethods.verifyTokenInvalidUsernotFound(tokenHeader);
        if (response!=null){
            return response;
        }
        String tokenCleaned = tokenService.replaceToken(tokenHeader);
        long idCompany = Long.parseLong(tokenService.validateToken(tokenCleaned));
        response = jobService.updateJob(updateJobDto, idCompany, job_id);
        /*if (created) {
            return ResponseEntity.status(200).body(new ApiResponse("Updated"));
        } else {
            ResponseEntity.status(500).body("Error while saving: ");
        }*/
        return response;
    }
    @DeleteMapping("/{job_id}")
    public ResponseEntity<?>deleteJob(@RequestHeader("Authorization") String tokenHeader, @PathVariable int job_id){
        ResponseEntity<?> response = null;
        response = verificarionMethods.verifyTokenInvalidUsernotFound(tokenHeader);
        if (response!=null){
            return response;
        }

        String tokenCleaned = tokenService.replaceToken(tokenHeader);
        long idCompany = Long.parseLong(tokenService.validateToken(tokenCleaned));
        ResponseEntity<?> deleted = jobService.deleteJob(idCompany, job_id);
        if (deleted != null) {
            return deleted;
        }
        return ResponseEntity.status(500).body("Error while deleting ");
    }
    @PostMapping("/search")
    public ResponseEntity<?> getAllJobsWithFilter(@RequestHeader("Authorization") String tokenHeader ,@RequestBody @Valid JobSearchDto jobSearchDto) {
        //String tokenCleaned = tokenService.replaceToken(tokenHeader);
        //long idCompany = Long.parseLong(tokenService.validateToken(tokenCleaned));
        ResponseEntity<?> response = verificarionMethods.verifyTokenInvalidUsernotFound(tokenHeader);
        if (response!=null){
            return response;
        }
        //List<Job> jobs = jobService.searchJobsWithSpecs(jobSearchDto.filters().getFirst());

        List<JobDto> jobsSearchDtos = jobSearchDto.filters().stream() //split the various filters
                .map(filterDto -> jobService.searchJobsWithSpecs(filterDto)) //for each filter, does a sql, various jobs list
                .flatMap(List::stream)//gather the jobs lists in just one
                .distinct()
                .map(JobDto::new) //for each job -> JobDto
                .toList();

        if (!jobsSearchDtos.isEmpty()) {
            //List<JobDto> jobDtos = jobs.stream().map(JobDto::new).toList();
            return ResponseEntity.status(200).body(new JobsSearchDto(jobsSearchDtos));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse("Job not found"));
        }
    }
    @PostMapping("{job_id}")
    ResponseEntity<?> applyForJob(@RequestHeader("Authorization") String tokenHeader,@RequestBody @Valid ApplicationDto applicationDto, @PathVariable long job_id){
        String tokenCleaned = tokenService.replaceToken(tokenHeader);
        long idUser = Long.parseLong(tokenService.validateToken(tokenCleaned));
        ResponseEntity response = verificarionMethods.verifyTokenInvalidUsernotFound(tokenHeader);
        if (response!=null){return response;}
        response = jobService.applyForJob(applicationDto, job_id, idUser);
        if (response!=null) {
            return response;
        } else {
            return ResponseEntity.status(500).body("Error");
        }
    }
}
