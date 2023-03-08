package br.com.caiolobo.blogapplication.services;

import br.com.caiolobo.blogapplication.dto.AccountDTO;
import br.com.caiolobo.blogapplication.dto.PostDTO;
import br.com.caiolobo.blogapplication.exceptions.PostNotFoundException;
import br.com.caiolobo.blogapplication.exceptions.UserNotFoundException;
import br.com.caiolobo.blogapplication.models.Account;
import br.com.caiolobo.blogapplication.models.Post;
import br.com.caiolobo.blogapplication.repositories.AccountRepository;
import br.com.caiolobo.blogapplication.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
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

    public PostDTO save(PostDTO postDto, String emailAccount){
        Account account = accountRepository.findByEmail(emailAccount);
        if(account == null){
            throw new UserNotFoundException();
        }
        if(postDto.getId() == null){
            postDto.setCreatedAt(LocalDateTime.now());
            postDto.setAccount(convertAccountToDto(account));
        }
        System.out.println("parei aqui");
        Post post = postRepository.save(convertDtoToPost(postDto));
        postDto.setId(post.getId());
        return postDto;
    }

    public PostDTO getById(Long id){
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException());
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






    public PostDTO convertPostToDto(Post post){
        if(post == null){
            return null;
        }
        PostDTO postDto = new PostDTO();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setId(post.getId());
        postDto.setCreatedAt(post.getCreatedAt());
        postDto.setBody(post.getBody());
        postDto.setAccount(convertAccountToDto(post.getAccount()));
        return postDto;
    }
    public List<PostDTO> convertPostsToDto(List<Post> posts) {

        return posts.stream()
                .map(this::convertPostToDto)
                .collect(Collectors.toList());
    }


    /*private PostDTO convertSinglePost(Post post){
        PostDTO postDto = new PostDTO();
        postDto.setTitle(post.getTitle());
        postDto.setId(post.getId());
        postDto.setCreatedAt(post.getCreatedAt());
        postDto.setBody(post.getBody());
        postDto.setAccount(convertAccountToDto(post.getAccount()));
        return postDto;
    }*/
    public String testeRetorno(){
        return "testeeee";
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

    public Account convertDtoToAccount(AccountDTO accountDto){
        return accountRepository.findById(accountDto.getId()).orElseThrow(UserNotFoundException::new);
    }

    private Set<String> addAccountAuthoritiesToDto(Account account){
        Set<String> authorities = new HashSet<>();
        account.getAuthorities().stream()
                .map(authority -> authorities.add(String.valueOf(authority)))
                .collect(Collectors.toSet());
        return authorities;
    }
}
