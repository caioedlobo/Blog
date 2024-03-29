package br.com.caiolobo.blogapplication.controllers;

import br.com.caiolobo.blogapplication.auth.AuthenticationRequest;
import br.com.caiolobo.blogapplication.models.PasswordRequest;
import br.com.caiolobo.blogapplication.models.entities.Account;
import br.com.caiolobo.blogapplication.services.JwtService;
import br.com.caiolobo.blogapplication.dto.AccountDTO;
import br.com.caiolobo.blogapplication.dto.AccountUpdateDTO;
import br.com.caiolobo.blogapplication.models.View;
import br.com.caiolobo.blogapplication.services.AccountService;
import br.com.caiolobo.blogapplication.services.EmailService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Account", description = "This endpoint allows for the creation, reading, updating, and deletion of accounts.")
@RestController
@RequestMapping(value = "/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;
    private final JwtService jwtService;

    public AccountController(AccountService accountService, JwtService jwtService) {
        this.accountService = accountService;
        this.jwtService = jwtService;
    }

    @Operation(summary = "Get Account by ID")
    @GetMapping(value = "/{id}")
    //@JsonView(View.ExcludeAccountFromPost.class)
    @JsonView(View.Base.class)
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable("id") Long id){
        return ResponseEntity.ok(accountService.findById(id));
    }

    @Operation(summary = "Update Account name")
    @PutMapping(value = "/update-name")
    public ResponseEntity<AccountUpdateDTO> updateAccountName(HttpServletRequest request, @RequestBody AccountUpdateDTO accountUpdateDTO){
        accountService.updateName(jwtService.getEmailFromRequest(request), accountUpdateDTO);
        return ResponseEntity.ok().body(accountUpdateDTO);
    }
    @Operation(summary = "Update Account password")
    @PutMapping(value = "/update-password")
    public ResponseEntity<AuthenticationRequest> updateAccountPassword(HttpServletRequest request, @Valid @RequestBody PasswordRequest passwordRequest){
        accountService.updatePassword(jwtService.getEmailFromRequest(request), passwordRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete Account")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAccount(HttpServletRequest request){
        accountService.delete(jwtService.getEmailFromRequest(request));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get ID by Account logged in")
    @GetMapping("/account-id")
    public ResponseEntity<Long> getAccountLoggedId(HttpServletRequest request){
        return ResponseEntity.ok().body(accountService.getAccountId(jwtService.getEmailFromRequest(request)));
    }
}
