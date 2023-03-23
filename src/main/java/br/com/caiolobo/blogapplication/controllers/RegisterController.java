package br.com.caiolobo.blogapplication.controllers;

import br.com.caiolobo.blogapplication.auth.AuthenticationRequest;
import br.com.caiolobo.blogapplication.auth.AuthenticationResponse;

import br.com.caiolobo.blogapplication.auth.AuthenticationService;
import br.com.caiolobo.blogapplication.auth.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;

@Tag(name = "Authenticate", description = "This endpoint allows for the creation and authentication of accounts.")
@RestController
@RequestMapping(value = "/api/auth")
public class RegisterController {

    @Autowired
    private AuthenticationService authenticationService;

    @Operation(summary = "Register an user")
    @ApiResponse(responseCode = "201")
    @PostMapping(value = "/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @Operation(summary = "Authenticate user")
    @PostMapping(value = "/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
