package br.com.caiolobo.blogapplication.controllers;

import br.com.caiolobo.blogapplication.dto.AccountDTO;
import br.com.caiolobo.blogapplication.models.Account;
import br.com.caiolobo.blogapplication.models.View;
import br.com.caiolobo.blogapplication.services.AccountService;
import br.com.caiolobo.blogapplication.services.EmailService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/accounts")
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
    public String sendEmailMessage(){
        emailService.sendMessage(
                "caioeduardolobo@gmail.com",
                "Teste",
                "Texto de teste");
        return "Mensagem enviada";
    }
}
