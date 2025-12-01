package com.utfpr.Projeto_Sistemas.controller;

import com.utfpr.Projeto_Sistemas.config.TokenService;
import com.utfpr.Projeto_Sistemas.dto.ErrorDto;
import com.utfpr.Projeto_Sistemas.dto.job.ApplicationDto;
import com.utfpr.Projeto_Sistemas.utilities.VerificarionMethods;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/error")
@Slf4j
public class FallBackController {

    @Autowired
    TokenService tokenService;
    @Autowired
    VerificarionMethods verificarionMethods;


    @PostMapping
    public void error(@RequestHeader("Authorization") String tokenHeader, @RequestBody @Valid ErrorDto errorDto){
        /*String tokenCleaned = tokenService.replaceToken(tokenHeader);
        ResponseEntity response = verificarionMethods.verifyTokenInvalid(tokenHeader);
        if (response!=null){return response;}*/
        log.info(errorDto.message());
    }
}
