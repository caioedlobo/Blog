package br.com.caiolobo.blogapplication.services;

import br.com.caiolobo.blogapplication.auth.AuthenticationRequest;
import br.com.caiolobo.blogapplication.dto.AccountDTO;
import br.com.caiolobo.blogapplication.dto.AccountUpdateDTO;
import br.com.caiolobo.blogapplication.exceptions.AccountAlreadyExistsException;
import br.com.caiolobo.blogapplication.exceptions.AccountNotFoundException;
import br.com.caiolobo.blogapplication.mappers.AccountMapper;
import br.com.caiolobo.blogapplication.models.PasswordRequest;
import br.com.caiolobo.blogapplication.models.entities.Account;
import br.com.caiolobo.blogapplication.repositories.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostDeletionService postDeletionService;
    private final AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, PostDeletionService postDeletionService, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.postDeletionService = postDeletionService;
        this.accountMapper = accountMapper;
    }

    public Account save(Account account){
        Account newAccount = accountRepository.findByEmail(account.getEmail());
        if( newAccount == null){
            return accountRepository.save(account);
        }
        throw new AccountAlreadyExistsException();
    }
    public List<Account> getAll(){
        return accountRepository.findAll();
    }

    public Optional<Account> findByEmail(String email){
        return Optional.ofNullable(accountRepository.findByEmail(email));
    }

    public AccountDTO findById(Long id){
        return accountMapper.toDto(accountRepository.findById(id).orElseThrow(AccountNotFoundException::new));
    }

    public void updateName(String email, AccountUpdateDTO accountUpdateDTO){
        Account account = accountRepository.findByEmail(email);
        account.setFirstName(accountUpdateDTO.getFirstName());
        account.setLastName(accountUpdateDTO.getLastName());
        accountRepository.save(account);

    }

    /*private Set<String> addAccountAuthoritiesToDto(Account account){
        Set<String> authorities = new HashSet<>();
        account.getAuthorities().stream()
                .map(authority -> authorities.add(String.valueOf(authority)))
                .collect(Collectors.toSet());
        return authorities;
    }*/


    public void updatePassword(String email, PasswordRequest passwordRequest) {
        Account account = accountRepository.findByEmail(email);
        account.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
        accountRepository.save(account);
    }

    public Long getAccountId(String email) {
        Account account = accountRepository.findByEmail(email);
        return account.getId();
    }

    public void delete(String emailFromRequest) {
        Account account = accountRepository.findByEmail(emailFromRequest);
        postDeletionService.deleteAllPosts(account.getId());
        accountRepository.delete(account);
    }
}
