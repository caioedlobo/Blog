package br.com.caiolobo.blogapplication.controllers;

import br.com.caiolobo.blogapplication.models.Account;
import br.com.caiolobo.blogapplication.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/register")
public class RegisterController {

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping
    public ResponseEntity<Account> register(@RequestBody Account account){

        return ResponseEntity.ok(accountRepository.save(account));
    }
}
