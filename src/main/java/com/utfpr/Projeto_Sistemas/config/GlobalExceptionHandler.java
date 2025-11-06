package com.utfpr.Projeto_Sistemas.config;

import com.utfpr.Projeto_Sistemas.utilities.FieldError;
import com.utfpr.Projeto_Sistemas.utilities.FieldMessage;
import jakarta.servlet.http.HttpServletRequest;
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
    //private static final Gson gson = new Gson();
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationError(MethodArgumentNotValidException ex, HttpServletRequest req) {
        //BodyData bodyData = new BodyData();
        FieldMessage message = new FieldMessage();
        message.setStatus(400);
        message.setField("Validation Error");
        message.setError(ex.getFieldErrors().stream()
                .map(e -> new FieldError(e.getField(), e.getDefaultMessage()))
                .collect(Collectors.toList()));
        //HttpHeaders header = new HttpHeaders();
        //header.set("Status", String.valueOf(404));
        ResponseEntity<Object> response = new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
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
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<String> handleForbiddenException(Exception ex, HttpServletRequest req) {
        FieldMessage message = new FieldMessage();
        message.setMessage("Invalid token");
        ResponseEntity<String> response = new ResponseEntity<>(message.getMessage(), HttpStatus.FORBIDDEN);
        System.out.println("Response sent:"+ response);
        return response;
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericError(Exception ex, HttpServletRequest req) {
        FieldMessage message = new FieldMessage();
        message.setMessage(ex.getMessage());
        ResponseEntity<String> response = new ResponseEntity<>(message.getMessage(), HttpStatus.BAD_REQUEST);
        System.out.println("Response sent:"+ response);
        return response;
    }
}
