package br.com.caiolobo.blogapplication.controllers;

import br.com.caiolobo.blogapplication.dto.AccountDTO;
import br.com.caiolobo.blogapplication.models.View;
import br.com.caiolobo.blogapplication.services.AccountService;
import br.com.caiolobo.blogapplication.services.EmailService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private EmailService emailService;

    @GetMapping(value = "/{id}")
    @JsonView(View.ExcludeAccountFromPost.class)
    public ResponseEntity<AccountDTO> getAccount(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(accountService.findById(id));
    }

    @PostMapping(value = "/message")
    public String sendEmailMessage(@RequestBody String email){
        emailService.sendMessage(
                email,
                "Recuperação de conta",
                "Texto de teste");
        return "Mensagem enviada";
    }
}
