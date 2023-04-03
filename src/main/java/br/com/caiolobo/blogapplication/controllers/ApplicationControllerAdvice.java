package br.com.caiolobo.blogapplication.controllers;

import br.com.caiolobo.blogapplication.ApiErrors;
import br.com.caiolobo.blogapplication.exceptions.PostNotFoundException;
import br.com.caiolobo.blogapplication.exceptions.UserNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleUserNotFound(UserNotFoundException ex){
        String message = ex.getMessage();
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handlePostNotFound(PostNotFoundException ex){
        String message = ex.getMessage();
        return new ApiErrors(message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleMethodNotValidException(MethodArgumentNotValidException ex ){
        List<String> errors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
        return new ApiErrors(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleConstraintViolationException(ConstraintViolationException ex ){
        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(error -> error.getMessage())
                .collect(Collectors.toList());
        return new ApiErrors(errors);
    }

}
