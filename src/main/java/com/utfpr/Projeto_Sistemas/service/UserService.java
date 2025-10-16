package com.utfpr.Projeto_Sistemas.service;

import com.utfpr.Projeto_Sistemas.controller.CreateUserDto;
import com.utfpr.Projeto_Sistemas.entities.Role;
import com.utfpr.Projeto_Sistemas.entities.User;
import com.utfpr.Projeto_Sistemas.repository.UserRepositoy;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepositoy userRepositoy;

    public UserService(UserRepositoy userRepositoy) {
        this.userRepositoy = userRepositoy;
    }

    public boolean createUser(CreateUserDto createUserDto) {
        //userDto -> user
        var Entity = new User(
                createUserDto.name(),
                createUserDto.username(),
                createUserDto.password(),
                createUserDto.email(),
                createUserDto.phone(),
                createUserDto.experience(),
                createUserDto.education(),
                Instant.now(),
                null, // date update
                Role.USER
        );

        User savedUser = userRepositoy.save(Entity);
        //check if user has been created
        return userRepositoy.existsById(savedUser.getIdUser());
    }

    public Optional<User> getUserByUsername(String username){
        return null;//userRepositoy.findByUsername(username);
    }
}

