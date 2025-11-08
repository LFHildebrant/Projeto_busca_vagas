package com.utfpr.Projeto_Sistemas.config;

import com.utfpr.Projeto_Sistemas.entities.ApiResponse;
import com.utfpr.Projeto_Sistemas.utilities.FieldError;
import com.utfpr.Projeto_Sistemas.utilities.FieldMessage;
import com.utfpr.Projeto_Sistemas.utilities.ValidationErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice()
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationError(MethodArgumentNotValidException ex, HttpServletRequest req) {
        ValidationErrorMessage validationMessage = new ValidationErrorMessage();
        validationMessage.setCode("UNPROCESSABLE");
        validationMessage.setMessage("Validation Error");
        validationMessage.setDetails(ex.getFieldErrors().stream()
                .map(e -> new FieldError(e.getField(), e.getDefaultMessage()))
                .collect(Collectors.toList()));
        ResponseEntity<Object> response = new ResponseEntity<>(validationMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        System.out.println("Response sent:"+ response);
        return response;
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest req) {
        ValidationErrorMessage validationMessage = new ValidationErrorMessage();
        validationMessage.setCode("UNPROCESSABLE");
        validationMessage.setMessage("Validation Error");
        validationMessage.setDetails(ex.getConstraintViolations().stream()
                .map(violation -> new FieldError(
                        violation.getPropertyPath().toString(),
                        violation.getMessage()
                ))
                .collect(Collectors.toList()));
        ResponseEntity<Object> response = new ResponseEntity<>(validationMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        System.out.println("Response sent:"+ response);
        return response;
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleCredentialsError(Exception ex, HttpServletRequest req) {
        FieldMessage message = new FieldMessage();
        message.setMessage("Invalid Credentials");
        ResponseEntity<String> response = new ResponseEntity<>(message.getMessage(), HttpStatus.UNAUTHORIZED);
        System.out.println("Response sent:"+ response);
        return response;
    }

    @ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
    public ResponseEntity<Object> handleDatabaseConstraintViolation(org.hibernate.exception.ConstraintViolationException ex) {

        ApiResponse validationMessage = new ApiResponse();
        String constraintName = ex.getConstraintName();
        if (constraintName != null) {
            String lowerConstraint = constraintName.toLowerCase();

            if (lowerConstraint.contains("username")) {
                validationMessage.setMessage("Username already exists");
            } else if (lowerConstraint.contains("name")) {
                validationMessage.setMessage("Company name already exists");
            }
        }
        return new ResponseEntity<>(validationMessage, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDatabaseConstraintViolation(DataIntegrityViolationException ex) {

        ApiResponse validationMessage = new ApiResponse();
        Throwable cause = ex.getRootCause();
        String rootCauseMessage = cause != null ? cause.getMessage().toLowerCase() : "";
        if (rootCauseMessage.contains("duplicate entry") || rootCauseMessage.contains("unique") ) {
            if (rootCauseMessage.contains("username")) {
                validationMessage.setMessage("Username already exists");
            } else if (rootCauseMessage.contains("name")) {
                validationMessage.setMessage("Company name already exists");
            }
        }
        return new ResponseEntity<>(validationMessage, HttpStatus.CONFLICT);
    }
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<String> handleForbiddenException(Exception ex, HttpServletRequest req) {
        FieldMessage message = new FieldMessage();
        message.setMessage("Invalid token");
        ResponseEntity<String> response = new ResponseEntity<>(message.getMessage(), HttpStatus.FORBIDDEN);
        System.out.println("Response sent:"+ response);
        return response;
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericError(Exception ex, HttpServletRequest req) {
        FieldMessage message = new FieldMessage();
        message.setStatus(400);
        message.setField("ERROR:");
        message.setMessage(ex.getMessage());
        ResponseEntity<Object> response = new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        System.out.println( ex + "Response sent:"+ response);
        return response;
    }
}
