package br.com.caiolobo.blogapplication.services;

import br.com.caiolobo.blogapplication.dto.AccountDTO;
import br.com.caiolobo.blogapplication.dto.PostDTO;
import br.com.caiolobo.blogapplication.exceptions.UserNotFoundException;
import br.com.caiolobo.blogapplication.models.Account;
import br.com.caiolobo.blogapplication.models.Post;
import br.com.caiolobo.blogapplication.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    private PostService postService;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account save(Account account){return accountRepository.save(account);
    }
    public List<Account> getAll(){
        return accountRepository.findAll();
    }

    public Optional<Account> findByEmail(String email){
        return Optional.ofNullable(accountRepository.findByEmail(email));
    }

    public AccountDTO findById(Long id){
        Account account = accountRepository.findById(id).orElseThrow(UserNotFoundException::new);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setEmail(account.getEmail());
        accountDTO.setAuthorities(addAccountAuthoritiesToDto(account));
        accountDTO.setFirstName(account.getFirstName());
        accountDTO.setLastName(account.getLastName());
        if(account.getPosts().isEmpty()){

            accountDTO.setPosts(Collections.emptyList());
        }
        else {
            accountDTO.setPosts(postService.convertPostsToDto(account.getPosts()));
        }
        return accountDTO;
    }



    public Account convertDtoToAccount(AccountDTO accountDto){
        return accountRepository.findById(accountDto.getId()).orElseThrow(UserNotFoundException::new);
    }

    public AccountDTO convertAccountToDto(Account account){
        return AccountDTO.builder()
                .id(account.getId())
                .email(account.getUsername())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                //.posts(convertPostToDto(account.getPosts()))
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

    public String hello(){
        return "Oláaaa";
    }

}
