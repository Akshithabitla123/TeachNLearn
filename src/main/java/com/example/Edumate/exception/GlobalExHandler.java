package com.example.Edumate.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExHandler {
    //validation error
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> handleValidation(MethodArgumentNotValidException ex){
        Map<String,String> errors=new HashMap<>();
        ex.getBindingResult().getFieldErrors()
            .forEach(error->{
                errors.put(error.getField(),error.getDefaultMessage());
            });
        return errors;
    }
    //auth failure
    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String,String> invalidCredentials(InvalidCredentialsException ex){
        Map<String,String> errors=new HashMap<>();
        errors.put("error",ex.getMessage());
        return errors;
    }
    //Runtime exceptions
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> runtime(RuntimeException ex){
        Map<String,String> errors=new HashMap<>();
        errors.put("error",ex.getMessage());
        return errors;
    }

}
