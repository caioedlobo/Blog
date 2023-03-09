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
    private AccountService accountService;


    @Autowired
    public PostService(PostRepository postRepository, AccountRepository accountRepository) {
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
            postDto.setAccount(accountService.convertAccountToDto(account));
        }

        Post post = postRepository.save(convertDtoToPost(postDto));
        postDto.setId(post.getId());
        return postDto;
    }

    public PostDTO getById(Long id){
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
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
                .account(accountService.convertDtoToAccount(postDto.getAccount()))
                .build();
    }


    public PostDTO convertPostToDto(Post post){
        System.out.println(post.getAccount());
        System.out.println("aquiii");
        PostDTO postDto = new PostDTO();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setId(post.getId());
        postDto.setCreatedAt(post.getCreatedAt());
        postDto.setBody(post.getBody());
        postDto.setAccount(accountService.convertAccountToDto(post.getAccount()));
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



    private Set<String> addAccountAuthoritiesToDto(Account account){
        Set<String> authorities = new HashSet<>();
        account.getAuthorities().stream()
                .map(authority -> authorities.add(String.valueOf(authority)))
                .collect(Collectors.toSet());
        return authorities;
    }
}
