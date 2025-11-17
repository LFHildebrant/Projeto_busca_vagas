package com.utfpr.Projeto_Sistemas.service;

import com.utfpr.Projeto_Sistemas.dto.user.CreateUserDto;
import com.utfpr.Projeto_Sistemas.dto.user.UpdateUserDto;
import com.utfpr.Projeto_Sistemas.entities.Role;
import com.utfpr.Projeto_Sistemas.entities.User;
import com.utfpr.Projeto_Sistemas.dto.user.UserDto;
import com.utfpr.Projeto_Sistemas.repository.UserRepositoy;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

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
                verifyNullable(createUserDto.email()),
                verifyNullable(createUserDto.phone()),
                verifyNullable(createUserDto.experience()),
                verifyNullable(createUserDto.education()),
                Instant.now(),
                null, // date update
                Role.USER
        );

        User savedUser = userRepositoy.save(Entity);
        //check if user has been created
        return userRepositoy.existsById(savedUser.getIdUser());
    }
    public String verifyNullable(String value){
        if (value == null || value.trim().isEmpty()){
            return null;
        }
        return value;
    }

    public boolean updateUser(UpdateUserDto updateUserDto, Long idUser) {
        User existingUser = (User) userRepositoy.findByIdUser(idUser);

        existingUser.setName(updateUserDto.name().toUpperCase()); //get the user from db, then put the new data on the user and save
        if (!updateUserDto.password().isEmpty()) {
            String encryptedPassword = new BCryptPasswordEncoder().encode(updateUserDto.password());
            existingUser.setPassword(encryptedPassword);
        }
        existingUser.setEmail(verifyNullable(updateUserDto.email()));
        existingUser.setPhone(verifyNullable(updateUserDto.phone()));
        existingUser.setExperience(verifyNullable(updateUserDto.experience()));
        existingUser.setEducation(verifyNullable(updateUserDto.education()));

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
        return result;
    }
}

