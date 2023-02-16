package br.com.caiolobo.blogapplication.controllers;

import br.com.caiolobo.blogapplication.dto.AccountDTO;
import br.com.caiolobo.blogapplication.models.Account;
import br.com.caiolobo.blogapplication.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(
                accountService.findById(id)
                        .orElseThrow(() -> new RuntimeException("Não foi possível obter o usuário.")));
    }
}