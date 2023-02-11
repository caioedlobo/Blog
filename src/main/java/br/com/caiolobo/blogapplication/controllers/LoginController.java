package br.com.caiolobo.blogapplication.controllers;

import br.com.caiolobo.blogapplication.models.Account;
import br.com.caiolobo.blogapplication.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    private AccountService accountService;
    @PostMapping
    public String login(String email, String password) {
        Account account = accountService.findByEmail(email)
    }
}
