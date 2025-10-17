package com.utfpr.Projeto_Sistemas.utilities;

import com.utfpr.Projeto_Sistemas.config.TokenService;
import com.utfpr.Projeto_Sistemas.controller.CreateUserDto;
import com.utfpr.Projeto_Sistemas.entities.ApiResponse;
import com.utfpr.Projeto_Sistemas.entities.UserDto;
import com.utfpr.Projeto_Sistemas.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VerificarionMethods {

    private final TokenService tokenService;
    private final UserService userService;

    public VerificarionMethods(TokenService tokenService, UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    public ResponseEntity<?> verifyTokenInvalidForbiddenUsernotFound(String tokenHeader, int user_id){
        if (!tokenService.verifyToken(tokenHeader)){  //verify received token
            return ResponseEntity.status(401).body(new ApiResponse("Invalid Token"));
        }
        String tokenCleaned = tokenService.replaceToken(tokenHeader);
        long idUser = Long.parseLong(tokenService.validateToken(tokenCleaned)); //idUser from token
        System.out.println("exists? " + userService.existsUserById(idUser));
        if (!userService.existsUserById(idUser)){
            System.out.println("exists? " + userService.existsUserById(idUser));
            return ResponseEntity.status(404).body(new ApiResponse("User not found"));
        }
        if (idUser != user_id){
            return ResponseEntity.status(403).body(new ApiResponse("Forbidden"));
        }
        return null;
    }

    public ResponseEntity<?> VerificationUserFieldCreate(CreateUserDto createUserDto) {

        ValidationErrorMessage errorMessage = new ValidationErrorMessage();
        errorMessage.setMessage("Validation Error");
        errorMessage.setCode("UNPROCESSABLE");  //Message creation
        errorMessage.setDetails(new ArrayList<FieldMessage>());

        //username null / username regex / username already exists
        if (createUserDto.username() == null || createUserDto.username().isEmpty()) {
            errorMessage.getDetails().add(new FieldMessage("username", "username is required"));
        } else if (!createUserDto.username().matches("^[0-9a-zA-Z]{3,20}$")) {
            errorMessage.getDetails().add(new FieldMessage("username", "invalid_format"));
        } else if (this.userService.getUserByUsername(createUserDto.username())  != null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("username already exists"));
        }
        //name null / name length
        if (createUserDto.name() == null || createUserDto.name().isEmpty()) {
            errorMessage.getDetails().add(new FieldMessage("name", "name is required"));
        } else if (createUserDto.name().length() < 4 || createUserDto.name().length() > 150) {
            errorMessage.getDetails().add(new FieldMessage("name", "name must be between 4 and 150 characters"));
        }
        //password null / regex
        if (createUserDto.password() == null || createUserDto.password().isEmpty()) {
            errorMessage.getDetails().add(new FieldMessage("password", "password is required"));
        }else if (!createUserDto.password().matches("^[0-9a-zA-Z]{3,20}$")) {
            errorMessage.getDetails().add(new FieldMessage("password", "invalid_format"));
        }
        //email regex
        if (createUserDto.email() != null && !createUserDto.email().isEmpty()) {
            if (!createUserDto.email().matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")){
                errorMessage.getDetails().add(new FieldMessage("email", "invalid email"));
            }
        }
        //phone regex
        if (createUserDto.phone() != null && !createUserDto.phone().isEmpty()) {
            if (!createUserDto.email().matches("^(([1-9]{2})|[1-9]{2})9?[1-9]{4}-?[1-9]{4}$")){
                errorMessage.getDetails().add(new FieldMessage("phone", "invalid phone"));
            }
        }
        //experience length
        if (createUserDto.experience() != null && !createUserDto.experience().isEmpty()) {
            if (createUserDto.experience().length() < 10 || createUserDto.experience().length() > 600){
                errorMessage.getDetails().add(new FieldMessage("experience", "experience must be between 10 and 600 characters"));
            }
        }
        //education length
        if (createUserDto.education() != null && !createUserDto.education().isEmpty()) {
            if (createUserDto.education().length() < 10 || createUserDto.education().length() > 600){
                errorMessage.getDetails().add(new FieldMessage("education", "education must be between 10 and 600 characters"));
            }
        }
        if (errorMessage.getDetails().isEmpty()){
            return null;
        }
        return ResponseEntity.status(422).body(errorMessage);
    }

    public ResponseEntity<?> VerificationUserFieldUpdate(CreateUserDto createUserDto) {
        ValidationErrorMessage errorMessage = new ValidationErrorMessage();
        errorMessage.setMessage("Validation Error");
        errorMessage.setCode("UNPROCESSABLE");  //Message creation
        errorMessage.setDetails(new ArrayList<FieldMessage>());

        if (createUserDto.username() != null && !createUserDto.username().isEmpty()) {
            errorMessage.getDetails().add(new FieldMessage("username", "can not change username"));
        }
        //name null / name length
        if (createUserDto.name() == null || createUserDto.name().isEmpty()) {
            errorMessage.getDetails().add(new FieldMessage("name", "name is required"));
        } else if (createUserDto.name().length() < 4 || createUserDto.name().length() > 150) {
            errorMessage.getDetails().add(new FieldMessage("name", "name must be between 4 and 150 characters"));
        }
        //password null / regex
        if (createUserDto.password() == null || createUserDto.password().isEmpty()) {
            errorMessage.getDetails().add(new FieldMessage("password", "password is required"));
        }else if (!createUserDto.password().matches("^[0-9a-zA-Z]{3,20}$")) {
            errorMessage.getDetails().add(new FieldMessage("password", "invalid_format"));
        }
        //email regex
        if (createUserDto.email() != null && !createUserDto.email().isEmpty()) {
            if (!createUserDto.email().matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")){
                errorMessage.getDetails().add(new FieldMessage("email", "invalid email"));
            }
        }
        //phone regex
        if (createUserDto.phone() != null && !createUserDto.phone().isEmpty()) {
            if (!createUserDto.email().matches("^(([1-9]{2})|[1-9]{2})9?[1-9]{4}-?[1-9]{4}$")){
                errorMessage.getDetails().add(new FieldMessage("phone", "invalid phone"));
            }
        }
        //experience length
        if (createUserDto.experience() != null && !createUserDto.experience().isEmpty()) {
            if (createUserDto.experience().length() < 10 || createUserDto.experience().length() > 600){
                errorMessage.getDetails().add(new FieldMessage("experience", "experience must be between 10 and 600 characters"));
            }
        }
        //education length
        if (createUserDto.education() != null && !createUserDto.education().isEmpty()) {
            if (createUserDto.education().length() < 10 || createUserDto.education().length() > 600){
                errorMessage.getDetails().add(new FieldMessage("education", "education must be between 10 and 600 characters"));
            }
        }
        if (errorMessage.getDetails().isEmpty()){
            return null;
        }
        return ResponseEntity.status(422).body(errorMessage);
    }

}
