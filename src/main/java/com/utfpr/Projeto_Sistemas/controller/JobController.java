package com.utfpr.Projeto_Sistemas.controller;

import com.utfpr.Projeto_Sistemas.config.TokenService;
import com.utfpr.Projeto_Sistemas.dto.job.CreateJobDto;
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
}
