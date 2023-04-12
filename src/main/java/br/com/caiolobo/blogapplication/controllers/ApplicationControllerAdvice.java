package br.com.caiolobo.blogapplication.controllers;

import br.com.caiolobo.blogapplication.exceptions.AccountAlreadyExistsException;
import br.com.caiolobo.blogapplication.exceptions.TokenExpiredException;
import br.com.caiolobo.blogapplication.models.ApiErrors;
import br.com.caiolobo.blogapplication.exceptions.PostNotFoundException;
import br.com.caiolobo.blogapplication.exceptions.AccountNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleAccountNotFound(AccountNotFoundException ex){
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

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiErrors handleInternalAuthenticationServiceException(InternalAuthenticationServiceException ex){
        return new ApiErrors("A conta não existe.");
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleBadCredentialsException(BadCredentialsException ex){
        return new ApiErrors("Dados fornecidos são inválidos.");
    }

    @ExceptionHandler(AccountAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrors handleAccountAlreadyExistsException(AccountAlreadyExistsException ex){
        return new ApiErrors("Conta já existe.");
    }

    @ExceptionHandler(TokenExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiErrors handleTokenExpiredException(TokenExpiredException ex){
        return new ApiErrors(ex.getMessage());
    }

}
