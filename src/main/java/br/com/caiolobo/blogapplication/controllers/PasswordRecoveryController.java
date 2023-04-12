package br.com.caiolobo.blogapplication.controllers;

import br.com.caiolobo.blogapplication.models.RecoveryPasswordRequest;
import br.com.caiolobo.blogapplication.services.PasswordRecoveryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forgot-password")
public class PasswordRecoveryController {

    PasswordRecoveryService passwordRecoveryService;

    public PasswordRecoveryController(PasswordRecoveryService passwordRecoveryService) {
        this.passwordRecoveryService = passwordRecoveryService;
    }

    @PostMapping
    public ResponseEntity<String> generateToken(@RequestBody RecoveryPasswordRequest request) throws Exception {
        passwordRecoveryService.generateToken(request);
        return ResponseEntity.ok().build();
    }
}
