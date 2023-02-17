package br.com.caiolobo.blogapplication.services;

import br.com.caiolobo.blogapplication.dto.AccountDTO;
import br.com.caiolobo.blogapplication.dto.PostDTO;
import br.com.caiolobo.blogapplication.exceptions.UserNotFoundException;
import br.com.caiolobo.blogapplication.models.Account;
import br.com.caiolobo.blogapplication.models.Authority;
import br.com.caiolobo.blogapplication.models.Post;
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

    public AccountDTO findById(Long id){
        Account account = accountRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setEmail(account.getEmail());
        accountDTO.setAuthorities(addAccountAuthoritiesToDto(account));
        accountDTO.setFirstName(account.getFirstName());
        accountDTO.setLastName(account.getLastName());
        accountDTO.setPosts(account.getPosts());
        return accountDTO;
        //return convertAccountToDto(account);

    }

    private AccountDTO convertAccountToDto(Account account){
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

    private List<PostDTO> convertPostToDto(List<Post> posts) {
        List<PostDTO> postsDtos = new ArrayList<>();
        posts.stream()
                .map(post -> postsDtos.add(convertSinglePost(post)))
                .collect(Collectors.toList());
        System.out.println(postsDtos);
        return postsDtos;
    }
    private PostDTO convertSinglePost(Post post){
        PostDTO postDto = new PostDTO();
        postDto.setTitle(post.getTitle());
        postDto.setId(post.getId());
        postDto.setCreatedAt(post.getCreatedAt());
        postDto.setBody(post.getBody());
        postDto.setAccount(convertAccountToDto(post.getAccount()));
        return postDto;
    }
}
