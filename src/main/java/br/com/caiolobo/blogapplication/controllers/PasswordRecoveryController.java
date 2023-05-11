package br.com.caiolobo.blogapplication.controllers;

import br.com.caiolobo.blogapplication.auth.AuthenticationRequest;
import br.com.caiolobo.blogapplication.models.PasswordRequest;
import br.com.caiolobo.blogapplication.models.RecoveryPasswordRequest;
import br.com.caiolobo.blogapplication.services.PasswordRecoveryService;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/forgot-password")
public class PasswordRecoveryController {

    PasswordRecoveryService passwordRecoveryService;

    public PasswordRecoveryController(PasswordRecoveryService passwordRecoveryService) {
        this.passwordRecoveryService = passwordRecoveryService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<String> generatePasswordToken(@RequestBody RecoveryPasswordRequest request) {
        passwordRecoveryService.generateToken(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Se a conta existir no sistema, foi enviado um email.");
    }

    @PutMapping("/{token}")
    public ResponseEntity<Void> changeAccountPassword(@Valid @PathVariable("token") String token, @RequestBody PasswordRequest request){
        passwordRecoveryService.changePassword(request.getPassword(), token);
        return ResponseEntity.ok().build();
    }
}
