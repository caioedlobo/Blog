package br.com.caiolobo.blogapplication.controllers;

import br.com.caiolobo.blogapplication.auth.AuthenticationRequest;
import br.com.caiolobo.blogapplication.auth.AuthenticationResponse;

import br.com.caiolobo.blogapplication.services.AuthenticationService;
import br.com.caiolobo.blogapplication.auth.RegisterRequest;
import br.com.caiolobo.blogapplication.services.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Tag(name = "Authenticate", description = "This endpoint allows for the creation and authentication of accounts.")
@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    public AuthenticationController(AuthenticationService authenticationService, JwtService jwtService) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
    }

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

    @GetMapping("/is-authenticated")
    public ResponseEntity verifyIsAuthenticated(HttpServletRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        jwtService.isTokenValid(request, userDetails);
        return ResponseEntity.ok().build();
    }

}
