package com.utfpr.Projeto_Sistemas.controller;

import com.utfpr.Projeto_Sistemas.config.TokenService;
import com.utfpr.Projeto_Sistemas.entities.ApiResponse;
import com.utfpr.Projeto_Sistemas.repository.UserRepositoy;
import com.utfpr.Projeto_Sistemas.service.UserService;
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

    public UserController(UserService userService, UserRepositoy userRepositoy, TokenService tokenService) {
        this.userService = userService;
        this.userRepositoy = userRepositoy;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody CreateUserDto createUserDto){
        if (this.userRepositoy.findByUsername(createUserDto.username())!=null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Username already exists"));
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(createUserDto.password());
        CreateUserDto userDto = new CreateUserDto(createUserDto.username(), encryptedPassword, createUserDto.email(), createUserDto.name(), createUserDto.phone(), createUserDto.experience(), createUserDto.education());
        boolean created = userService.createUser(userDto);
        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Created"));
        } else {
            ResponseEntity.status(500).body("Erro ao salvar: ");
        }
        return null;
    }

    @GetMapping("/todos")
    public String getTodos(){
        return "Todos aqui em ";
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getUser(@RequestHeader ("Authorization") String tokenHeader){
        if (tokenService.verifyToken(tokenHeader)){  //verify received token
            ResponseEntity.status(403).body(new ApiResponse("Invalid Token"));
        }
        String tokenCleaned = tokenService.replaceToken(tokenHeader);
        long idUser = Long.parseLong(tokenService.validateToken(tokenCleaned));

        
        return ResponseEntity.status(200).body(idUser);
    }
}
