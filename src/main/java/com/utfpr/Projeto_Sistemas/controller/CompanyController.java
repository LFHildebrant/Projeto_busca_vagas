package com.utfpr.Projeto_Sistemas.controller;

import com.utfpr.Projeto_Sistemas.config.TokenService;
import com.utfpr.Projeto_Sistemas.config.TokenWhitelist;
import com.utfpr.Projeto_Sistemas.entities.ApiResponse;
import com.utfpr.Projeto_Sistemas.repository.CompanyRepository;
import com.utfpr.Projeto_Sistemas.repository.UserRepositoy;
import com.utfpr.Projeto_Sistemas.utilities.VerificarionMethods;
import com.utfpr.Projeto_Sistemas.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final VerificarionMethods verificarionMethods;
    private final TokenService tokenService;

    public CompanyController(CompanyService companyService, TokenService tokenService, VerificarionMethods verificarionMethods) {
        this.companyService = companyService;
        this.tokenService = tokenService;
        this.verificarionMethods = verificarionMethods;
    }

    @PostMapping
    public ResponseEntity<?> addCompany(@RequestBody @Valid CreateCompanyDto createCompanyDto, BindingResult result ){

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        } //else if (verificarionMethods.VerificarionUserExists(createCompanyDto.username()) != null){
            //return ResponseEntity.status(409).body(new ApiResponse("username already exists"));
        //}
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
        System.out.println("AQUI1111111" );
        response = verificarionMethods.verifyTokenInvalidForbiddenUsernotFound(tokenHeader, company_id);
        if (response!=null){
            return response;
        }
        String tokenCleaned = tokenService.replaceToken(tokenHeader);
        long idCompany = Long.parseLong(tokenService.validateToken(tokenCleaned));
        System.out.println("AQUI" + idCompany);
        return ResponseEntity.status(200).body(companyService.getDataCompany(idCompany));
    }
    @PatchMapping("/{company_id}")
    public ResponseEntity<?> editUser (@RequestHeader ("Authorization") String tokenHeader, @RequestBody  @Valid CreateCompanyDto createCompanyDto, @PathVariable int company_id){
        ResponseEntity<?> response = null;
        response = verificarionMethods.verifyTokenInvalidForbiddenUsernotFound(tokenHeader, company_id);
        if (response!=null){
            return response;
        }
        /*response = verificarionMethods.VerificationUserFieldUpdate(createUserDto);
        if (response != null) {
            return response;
        }*/
        String tokenCleaned = tokenService.replaceToken(tokenHeader);
        long idUser = Long.parseLong(tokenService.validateToken(tokenCleaned));
        String encryptedPassword = new BCryptPasswordEncoder().encode(createCompanyDto.password());
        CreateCompanyDto companyDto = new CreateCompanyDto(createCompanyDto.username(), encryptedPassword, createCompanyDto.email(), createCompanyDto.name(), createCompanyDto.business(), createCompanyDto.phone(), createCompanyDto.street(), createCompanyDto.city(), createCompanyDto.state(), createCompanyDto.number());
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
}
