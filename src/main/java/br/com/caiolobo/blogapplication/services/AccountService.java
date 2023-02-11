package br.com.caiolobo.blogapplication.services;

import br.com.caiolobo.blogapplication.models.Account;
import br.com.caiolobo.blogapplication.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
}
