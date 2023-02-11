package br.com.caiolobo.blogapplication.controllers;

import br.com.caiolobo.blogapplication.models.Account;
import br.com.caiolobo.blogapplication.repositories.AuthorityRepository;
import br.com.caiolobo.blogapplication.services.AccountService;
import br.com.caiolobo.blogapplication.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    AuthorityRepository authorityRepository;
    @PostMapping
    public void login(String email, String password) {
        System.out.println(myUserDetailsService.loadUserByUsername(email));
    }
}
