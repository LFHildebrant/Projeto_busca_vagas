package com.utfpr.Projeto_Sistemas.controller;

import com.utfpr.Projeto_Sistemas.config.TokenService;
import com.utfpr.Projeto_Sistemas.entities.ApiResponse;
import com.utfpr.Projeto_Sistemas.repository.UserRepositoy;
import com.utfpr.Projeto_Sistemas.service.UserService;
import com.utfpr.Projeto_Sistemas.utilities.VerificarionMethods;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRepositoy userRepositoy;
    private final TokenService tokenService;
    private final VerificarionMethods verificarionMethods;

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

    @GetMapping("/todos")
    public String getTodos(){
        return "Todos aqui em ";
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
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Created"));
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
            return ResponseEntity.status(200).body(new ApiResponse("Deleted"));
        }
        return ResponseEntity.status(500).body("Error while deleting: ");
    }
}
