package com.utfpr.Projeto_Sistemas.controller;

import com.utfpr.Projeto_Sistemas.config.TokenService;
import com.utfpr.Projeto_Sistemas.config.TokenWhitelist;
import com.utfpr.Projeto_Sistemas.entities.ApiResponse;
import com.utfpr.Projeto_Sistemas.repository.UserRepositoy;
import com.utfpr.Projeto_Sistemas.service.UserService;
import com.utfpr.Projeto_Sistemas.utilities.VerificarionMethods;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.Clock;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRepositoy userRepositoy;
    private final TokenService tokenService;
    private final VerificarionMethods verificarionMethods;

    @Autowired
    public UserController(UserService userService, UserRepositoy userRepositoy, TokenService tokenService, VerificarionMethods verificarionMethods) {
        this.userService = userService;
        this.userRepositoy = userRepositoy;
        this.tokenService = tokenService;
        this.verificarionMethods = verificarionMethods;
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody CreateUserDto createUserDto){

        ResponseEntity<?> response = verificarionMethods.VerificationUserFieldCreate(createUserDto);
        if (response != null) {
            return response;
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(createUserDto.password());
        CreateUserDto userDto = new CreateUserDto(createUserDto.username(), encryptedPassword, createUserDto.email(), createUserDto.name(), createUserDto.phone(), createUserDto.experience(), createUserDto.education());
        boolean created = userService.createUser(userDto);
        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Created"));
        } else {
            ResponseEntity.status(500).body("Error while saving: ");
        }
        return null;
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getUser(@RequestHeader ("Authorization") String tokenHeader, @PathVariable int user_id){
        ResponseEntity<?> response = null;
        response = verificarionMethods.verifyTokenInvalidForbiddenUsernotFound(tokenHeader, user_id);
        if (response!=null){
            return response;
        }
        String tokenCleaned = tokenService.replaceToken(tokenHeader);
        long idUser = Long.parseLong(tokenService.validateToken(tokenCleaned));
        return ResponseEntity.status(200).body(userService.getDataUser(idUser));
    }

    @PatchMapping("/{user_id}")
    public ResponseEntity<?> editUser (@RequestHeader ("Authorization") String tokenHeader, @RequestBody CreateUserDto createUserDto, @PathVariable int user_id){
        ResponseEntity<?> response = null;
        response = verificarionMethods.verifyTokenInvalidForbiddenUsernotFound(tokenHeader, user_id);
        if (response!=null){
            return response;
        }
        response = verificarionMethods.VerificationUserFieldUpdate(createUserDto);
        if (response != null) {
            return response;
        }
        String tokenCleaned = tokenService.replaceToken(tokenHeader);
        long idUser = Long.parseLong(tokenService.validateToken(tokenCleaned));
        String encryptedPassword = new BCryptPasswordEncoder().encode(createUserDto.password());
        CreateUserDto userDto = new CreateUserDto(createUserDto.username(), encryptedPassword, createUserDto.email(), createUserDto.name(), createUserDto.phone(), createUserDto.experience(), createUserDto.education());
        boolean created = userService.updateUser(userDto, idUser);
        if (created) {
            return ResponseEntity.status(200).body(new ApiResponse("Updated"));
        } else {
            ResponseEntity.status(500).body("Error while saving: ");
        }
        return null;
    }
    @DeleteMapping("/{user_id}")
    public ResponseEntity<?>deleteUser(@RequestHeader("Authorization") String tokenHeader, @PathVariable int user_id){
        ResponseEntity<?> response = null;
        response = verificarionMethods.verifyTokenInvalidForbiddenUsernotFound(tokenHeader, user_id);
        if (response!=null){
            return response;
        }

        String tokenCleaned = tokenService.replaceToken(tokenHeader);
        long idUser = Long.parseLong(tokenService.validateToken(tokenCleaned));
        int deleted = userService.deleteUser(idUser);
        if (deleted > 0) {
            TokenWhitelist tokenWhitelist = new TokenWhitelist();
            tokenWhitelist.remove(tokenCleaned);
            return ResponseEntity.status(200).body(new ApiResponse("Deleted"));
        }
        return ResponseEntity.status(500).body("Error while deleting: ");
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handleSQLError(SQLException e, HttpServletRequest req) {
        ApiResponse bodyData = new ApiResponse("Username already exists");

        HttpHeaders header = new HttpHeaders();
        header.set("Status",String.valueOf(HttpStatus.CONFLICT.value()));
        ResponseEntity<String> response = new ResponseEntity<>(bodyData.toString(),header,HttpStatus.CONFLICT);
        System.out.println("Response sent:"+ response);
        return response;
    }
}
