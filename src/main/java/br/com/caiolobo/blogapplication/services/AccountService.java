package br.com.caiolobo.blogapplication.services;

import br.com.caiolobo.blogapplication.models.Account;
import br.com.caiolobo.blogapplication.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void save(Account account){accountRepository.save(account);
    }

    public List<Account> getAll(){
        return accountRepository.findAll();
    }
}
