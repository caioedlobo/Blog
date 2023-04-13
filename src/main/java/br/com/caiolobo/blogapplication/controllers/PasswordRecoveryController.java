package br.com.caiolobo.blogapplication.controllers;

import br.com.caiolobo.blogapplication.auth.AuthenticationRequest;
import br.com.caiolobo.blogapplication.models.RecoveryPasswordRequest;
import br.com.caiolobo.blogapplication.services.PasswordRecoveryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/forgot-password")
public class PasswordRecoveryController {

    PasswordRecoveryService passwordRecoveryService;

    public PasswordRecoveryController(PasswordRecoveryService passwordRecoveryService) {
        this.passwordRecoveryService = passwordRecoveryService;
    }

    @PostMapping
    public ResponseEntity generateToken(@RequestBody RecoveryPasswordRequest request) throws Exception {
        passwordRecoveryService.generateToken(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{token}")
    public ResponseEntity changeAccountPassword(@Valid @PathVariable("token") String token, @RequestBody AuthenticationRequest request){
        passwordRecoveryService.changePassword(request.getPassword(), token);
        return ResponseEntity.ok().build();
    }
}
