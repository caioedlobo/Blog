package br.com.caiolobo.blogapplication.services;

import br.com.caiolobo.blogapplication.auth.AuthenticationRequest;
import br.com.caiolobo.blogapplication.dto.AccountUpdateDTO;
import br.com.caiolobo.blogapplication.exceptions.UserNotFoundException;
import br.com.caiolobo.blogapplication.models.entities.Account;
import br.com.caiolobo.blogapplication.repositories.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;

    private PostDeletionService postDeletionService;

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, PostDeletionService postDeletionService) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.postDeletionService = postDeletionService;
    }

    public Account save(Account account){return accountRepository.save(account);
    }
    public List<Account> getAll(){
        return accountRepository.findAll();
    }

    public Optional<Account> findByEmail(String email){
        return Optional.ofNullable(accountRepository.findByEmail(email));
    }

    public Account findById(Long id){
        Account account = accountRepository.findById(id).orElseThrow(UserNotFoundException::new);
        /*AccountDTO accountDTO = new AccountDTO();
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

        }*/
        return account;
    }

    public void updateName(String email, AccountUpdateDTO accountUpdateDTO){
        Account account = accountRepository.findByEmail(email);
        account.setFirstName(accountUpdateDTO.getFirstName());
        account.setLastName(accountUpdateDTO.getLastName());
        accountRepository.save(account);

    }

    private Set<String> addAccountAuthoritiesToDto(Account account){
        Set<String> authorities = new HashSet<>();
        account.getAuthorities().stream()
                .map(authority -> authorities.add(String.valueOf(authority)))
                .collect(Collectors.toSet());
        return authorities;
    }


    public void updatePassword(AuthenticationRequest authenticationRequest) {
        Account account = accountRepository.findByEmail(authenticationRequest.getEmail());
        account.setPassword(passwordEncoder.encode(authenticationRequest.getPassword()));
        accountRepository.save(account);

    }

    public void delete(String emailFromRequest) {
        Account account = accountRepository.findByEmail(emailFromRequest);
        //List<Post> posts = postService.findByEmail(emailFromRequest);
        postDeletionService.deleteAllPosts(account.getId());
        accountRepository.delete(account);
    }
}
