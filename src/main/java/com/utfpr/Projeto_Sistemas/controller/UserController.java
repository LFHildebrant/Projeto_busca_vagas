package com.utfpr.Projeto_Sistemas.controller;

import com.utfpr.Projeto_Sistemas.entities.ApiResponse;
import com.utfpr.Projeto_Sistemas.entities.User;
import com.utfpr.Projeto_Sistemas.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody CreateUserDto createUserDto){
        boolean created = userService.createUser(createUserDto);
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
}
