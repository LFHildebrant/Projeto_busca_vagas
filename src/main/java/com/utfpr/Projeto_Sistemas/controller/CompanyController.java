package com.utfpr.Projeto_Sistemas.controller;

import com.utfpr.Projeto_Sistemas.config.TokenService;
import com.utfpr.Projeto_Sistemas.config.TokenWhitelist;
import com.utfpr.Projeto_Sistemas.dto.company.CreateCompanyDto;
import com.utfpr.Projeto_Sistemas.dto.company.UpdateCompanyDto;
import com.utfpr.Projeto_Sistemas.dto.job.JobDto;
import com.utfpr.Projeto_Sistemas.dto.job.JobsSearchDto;
import com.utfpr.Projeto_Sistemas.dto.jobsearch.JobSearchDto;
import com.utfpr.Projeto_Sistemas.dto.user.UserJobDto;
import com.utfpr.Projeto_Sistemas.dto.user.UsersListByApplication;
import com.utfpr.Projeto_Sistemas.repository.ApplicationRepository;
import com.utfpr.Projeto_Sistemas.repository.JobRepository;
import com.utfpr.Projeto_Sistemas.service.JobService;
import com.utfpr.Projeto_Sistemas.utilities.ApiResponse;
import com.utfpr.Projeto_Sistemas.utilities.VerificarionMethods;
import com.utfpr.Projeto_Sistemas.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final VerificarionMethods verificarionMethods;
    private final TokenService tokenService;
    private final JobService jobService;
    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;

    public CompanyController(CompanyService companyService, TokenService tokenService, VerificarionMethods verificarionMethods, JobService jobService, ApplicationRepository applicationRepository, JobRepository jobRepository) {
        this.companyService = companyService;
        this.tokenService = tokenService;
        this.verificarionMethods = verificarionMethods;
        this.jobService = jobService;
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
    }

    @PostMapping
    public ResponseEntity<?> addCompany(@RequestBody @Valid CreateCompanyDto createCompanyDto){
        String encryptedPassword = new BCryptPasswordEncoder().encode(createCompanyDto.password());
        CreateCompanyDto companyDto = new CreateCompanyDto(createCompanyDto.username(), encryptedPassword, createCompanyDto.email(), createCompanyDto.name(), createCompanyDto.business(), createCompanyDto.phone(), createCompanyDto.street(), createCompanyDto.city(), createCompanyDto.state(), createCompanyDto.number());
        boolean created = companyService.createUser(companyDto);
        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Created"));
        } else {
            return ResponseEntity.status(500).body("Error while saving: ");
        }
    }

    @GetMapping("/{company_id}")
    public ResponseEntity<?> getCompany(@RequestHeader ("Authorization") String tokenHeader, @PathVariable int company_id){
        ResponseEntity<?> response = null;
        response = verificarionMethods.verifyTokenInvalidForbiddenUsernotFound(tokenHeader, company_id);
        if (response!=null){
            return response;
        }
        String tokenCleaned = tokenService.replaceToken(tokenHeader);
        long idCompany = Long.parseLong(tokenService.validateToken(tokenCleaned));
        return ResponseEntity.status(200).body(companyService.getDataCompany(idCompany));
    }
    @PatchMapping("/{company_id}")
    public ResponseEntity<?> updateCompany (@RequestHeader ("Authorization") String tokenHeader, @RequestBody  @Valid UpdateCompanyDto updateCompanyDto, @PathVariable int company_id){
        ResponseEntity<?> response = null;
        response = verificarionMethods.verifyTokenInvalidForbiddenUsernotFound(tokenHeader, company_id);
        if (response!=null){
            return response;
        }
        String tokenCleaned = tokenService.replaceToken(tokenHeader);
        long idUser = Long.parseLong(tokenService.validateToken(tokenCleaned));

        UpdateCompanyDto companyDto = new UpdateCompanyDto(updateCompanyDto.password(), updateCompanyDto.email(), updateCompanyDto.name(), updateCompanyDto.business(), updateCompanyDto.phone(), updateCompanyDto.street(), updateCompanyDto.city(), updateCompanyDto.state(), updateCompanyDto.number());
        boolean created = companyService.updateCompany(companyDto, idUser);
        if (created) {
            return ResponseEntity.status(200).body(new ApiResponse("Updated"));
        } else {
            ResponseEntity.status(500).body("Error while saving: ");
        }
        return null;
    }
    @DeleteMapping("/{company_id}")
    public ResponseEntity<?>deleteUser(@RequestHeader("Authorization") String tokenHeader, @PathVariable int company_id){
        ResponseEntity<?> response = null;
        response = verificarionMethods.verifyTokenInvalidForbiddenUsernotFound(tokenHeader, company_id);
        if (response!=null){
            return response;
        }

        String tokenCleaned = tokenService.replaceToken(tokenHeader);
        long idCompany = Long.parseLong(tokenService.validateToken(tokenCleaned));
        int deleted = companyService.deleteUser(idCompany);
        if (deleted > 0) {
            TokenWhitelist tokenWhitelist = new TokenWhitelist();
            tokenWhitelist.remove(tokenCleaned);
            return ResponseEntity.status(200).body(new ApiResponse("Deleted"));
        }
        return ResponseEntity.status(500).body("Error while deleting: ");
    }
    @PostMapping("/{company_id}/jobs")
    public ResponseEntity<?> getAllJobsByCompanyIdWithFilter(@RequestHeader("Authorization") String tokenHeader ,@RequestBody @Valid JobSearchDto jobSearchDto, @PathVariable long company_id) {
        ResponseEntity<?> response = verificarionMethods.verifyTokenInvalidForbiddenCompanynotFound(tokenHeader, company_id);
        if (response!=null){
            return response;
        }
        List<JobDto> jobsSearchDtos = jobSearchDto.filters().stream() //split the various filters
                .map(filterDto -> jobService.searchJobsByCompanyWithSpecs(filterDto, company_id)) //for each filter, does a sql, various jobs list
                .flatMap(List::stream)//gather the jobs lists in just one
                .distinct()
                .map(JobDto::new) //for each job -> JobDto
                .toList();
        if (!jobsSearchDtos.isEmpty()) {
            return ResponseEntity.status(200).body(new JobsSearchDto(jobsSearchDtos));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse("Job not found"));
        }
    }
    @GetMapping("/{company_id}/jobs/{job_id}")
    public ResponseEntity<?> getAllUsersByJob(@RequestHeader("Authorization") String tokenHeader, @PathVariable long company_id, @PathVariable int job_id) {
        ResponseEntity<?> response = verificarionMethods.verifyTokenInvalidForbiddenCompanynotFound(tokenHeader, company_id);
        if (response!=null){ return response;}
        return jobService.searchUsersByApplication(company_id, job_id);
    }
}
