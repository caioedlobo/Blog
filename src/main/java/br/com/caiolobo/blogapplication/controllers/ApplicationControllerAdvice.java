package br.com.caiolobo.blogapplication.controllers;

import br.com.caiolobo.blogapplication.ApiErrrors;
import br.com.caiolobo.blogapplication.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrrors handleUserNotFound(UserNotFoundException ex){
        String message =ex.getMessage();
        return new ApiErrrors(ex.getMessage());
    }
}
