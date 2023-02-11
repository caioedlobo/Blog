package br.com.caiolobo.blogapplication.controllers;

import br.com.caiolobo.blogapplication.models.Account;
import br.com.caiolobo.blogapplication.repositories.AccountRepository;
import br.com.caiolobo.blogapplication.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(value = "/register")
public class RegisterController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> register(@Valid @RequestBody Account account) {
        System.out.println(account);

        if (accountService.accountExists((account.getEmail()))) {
            throw new RuntimeException("Usuário já está cadastrado");
        }

        return ResponseEntity.ok(accountService.save(account));
    }

}
