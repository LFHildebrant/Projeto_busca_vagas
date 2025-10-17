package com.utfpr.Projeto_Sistemas.controller;

import com.utfpr.Projeto_Sistemas.config.TokenService;
import com.utfpr.Projeto_Sistemas.entities.ApiResponse;
import com.utfpr.Projeto_Sistemas.entities.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.time.Instant;

@RestController
@RequestMapping()
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody @Valid AuthenticationDto authenticationDto){
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDto.username(), authenticationDto.password());
            var auth = authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((User) auth.getPrincipal());
            return ResponseEntity.status(200).body(token);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ApiResponse("Invalid credentials"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout (@RequestHeader("Authorization") String tokenHeader){
        if (!tokenService.verifyToken(tokenHeader)){  //verify received token
            return ResponseEntity.status(403).body(new ApiResponse("Invalid Token"));
        }
        String tokenCleaned = tokenService.replaceToken(tokenHeader);
        Instant removed = tokenService.removeToken(tokenCleaned);
        if (removed == null){
            return ResponseEntity.status(403).body(new ApiResponse("Invalid Token"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("OK"));
    }
}
