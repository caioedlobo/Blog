package br.com.caiolobo.blogapplication.services;

import br.com.caiolobo.blogapplication.dto.AccountDTO;
import br.com.caiolobo.blogapplication.models.Account;
import br.com.caiolobo.blogapplication.models.Authority;
import br.com.caiolobo.blogapplication.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account save(Account account){return accountRepository.save(account);
    }
    public List<Account> getAll(){
        return accountRepository.findAll();
    }

    public Boolean accountExists(String email){
        if(accountRepository.findByEmail(email) == null){
            return false;   // se não encontrou
        }
        return true;
    }
    public Optional<Account> findByEmail(String email){
        return Optional.ofNullable(accountRepository.findByEmail(email));
    }

    public Optional<AccountDTO> findById(Long id){
        Account account = accountRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return Optional.ofNullable(convertAccountToDto(account));

    }

    private AccountDTO convertAccountToDto(Account account){
        return AccountDTO.builder()
                .id(account.getId())
                .email(account.getUsername())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .authorities(addAccountAuthoritiesToDto(account))
                .build();
    }

    private Set<String> addAccountAuthoritiesToDto(Account account){
        Set<String> authorities = new HashSet<>();
        account.getAuthorities().stream()
                .map(authority -> authorities.add(String.valueOf(authority)))
                .collect(Collectors.toSet());
        return authorities;
    }
}
