package br.com.caiolobo.blogapplication.services;

import br.com.caiolobo.blogapplication.dto.AccountDTO;
import br.com.caiolobo.blogapplication.dto.PostDTO;
import br.com.caiolobo.blogapplication.models.Account;
import br.com.caiolobo.blogapplication.models.Post;
import br.com.caiolobo.blogapplication.repositories.AccountRepository;
import br.com.caiolobo.blogapplication.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostService {

    private PostRepository postRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public PostService(PostRepository postRepository,
                       AccountRepository accountRepository) {
        this.postRepository = postRepository;
        this.accountRepository = accountRepository;
    }

    public Post save(PostDTO postDto){
        if(postDto.getId() == null){
            postDto.setCreatedAt(LocalDateTime.now());
        }
        return postRepository.save(convertDtoToPost(postDto));
    }

    public PostDTO getById(Long id){
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Teste"));
        //return convertPostToDto(post);
        return convertPostToDto(post);
    }

    public List<Post> getAll(){
        return postRepository.findAll();
    }

    private Post convertDtoToPost(PostDTO postDto){
        return Post.builder()
                .title(postDto.getTitle())
                .body(postDto.getBody())
                .createdAt(postDto.getCreatedAt())
                .account(convertDtoToAccount(postDto.getAccount()))
                .build();
    }

    private PostDTO convertPostToDto(Post post){
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .createdAt(post.getCreatedAt())
                .account(convertAccountToDto(post.getAccount()))
                .build();
    }

    private AccountDTO convertAccountToDto(Account account){
        return AccountDTO.builder()
                .id(account.getId())
                .email(account.getUsername())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .authorities(addAccountAuthoritiesToDto(account))
                .posts(account.getPosts())
                .build();
    }

    private Account convertDtoToAccount(AccountDTO accountDto){
        return accountRepository.findById(accountDto.getId()).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    private Set<String> addAccountAuthoritiesToDto(Account account){
        Set<String> authorities = new HashSet<>();
        account.getAuthorities().stream()
                .map(authority -> authorities.add(String.valueOf(authority)))
                .collect(Collectors.toSet());
        return authorities;
    }
}
