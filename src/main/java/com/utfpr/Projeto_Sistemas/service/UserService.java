package com.utfpr.Projeto_Sistemas.service;

import com.utfpr.Projeto_Sistemas.controller.CreateUserDto;
import com.utfpr.Projeto_Sistemas.entities.Role;
import com.utfpr.Projeto_Sistemas.entities.User;
import com.utfpr.Projeto_Sistemas.entities.UserDto;
import com.utfpr.Projeto_Sistemas.repository.UserRepositoy;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepositoy userRepositoy;

    public UserService(UserRepositoy userRepositoy) {
        this.userRepositoy = userRepositoy;
    }

    public boolean existsUserById(Long idUser){
        return userRepositoy.existsByIdUser(idUser);
    }

    public UserDto getDataUser(Long idUser){
        User user = (User) userRepositoy.findByIdUser(idUser);
        return new UserDto(user.getName(), user.getUsername(), user.getEmail(), user.getPhone(), user.getExperience(), user.getEducation());
    }

    public boolean createUser(CreateUserDto createUserDto) {
        //userDto -> user
        var Entity = new User(
                createUserDto.name().toUpperCase(),
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
    public boolean updateUser(CreateUserDto createUserDto, Long idUser) {
        User existingUser = (User) userRepositoy.findByIdUser(idUser);

        existingUser.setName(createUserDto.name().toUpperCase());
        existingUser.setPassword(createUserDto.password());  //get the user from db, then put the new data on the user and save
        existingUser.setEmail(createUserDto.email());
        existingUser.setPhone(createUserDto.phone());
        existingUser.setExperience(createUserDto.experience());
        existingUser.setEducation(createUserDto.education());

        User savedUser = userRepositoy.save(existingUser);
        //check if user has been created
        return userRepositoy.existsById(savedUser.getIdUser());
    }

    public UserDetails getUserByUsername(String username){
        return userRepositoy.findByUsername(username);
    }

    @Transactional
    public Integer deleteUser(Long idUser){
        Integer result = userRepositoy.deleteUserByIdUser(Math.toIntExact(idUser));
        userRepositoy.flush(); // sync db
        return result;    }
}

