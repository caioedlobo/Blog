package br.com.caiolobo.blogapplication.services;

import br.com.caiolobo.blogapplication.dto.PostDTO;
import br.com.caiolobo.blogapplication.exceptions.PostNotFoundException;
import br.com.caiolobo.blogapplication.exceptions.UserNotFoundException;
import br.com.caiolobo.blogapplication.mappers.AccountMapper;
import br.com.caiolobo.blogapplication.mappers.PostMapper;
import br.com.caiolobo.blogapplication.models.entities.Account;
import br.com.caiolobo.blogapplication.models.entities.Post;
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
    private AccountMapper accountMapper;
    private PostMapper postMapper;


    @Autowired
    public PostService(PostRepository postRepository, AccountRepository accountRepository, AccountMapper accountMapper, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.postMapper = postMapper;
    }

    public PostDTO save(PostDTO postDto, String emailAccount){
        Account account = accountRepository.findByEmail(emailAccount);
        if(account == null){
            throw new UserNotFoundException();
        }
        if(postDto.getId() == null){
            postDto.setCreatedAt(LocalDateTime.now());
            //*postDto.setAccount(accountService.convertAccountToDto(account));
            postDto.setAccount(accountMapper.toDto(account));
        }

        //Post post = postRepository.save(convertDtoToPost(postDto));
        Post post = postRepository.save(postMapper.toPost(postDto, account));

        postDto.setId(post.getId());
        return postDto;
    }

    public PostDTO findById(Long id){
        return postMapper.toDto(postRepository.findById(id).orElseThrow(PostNotFoundException::new));
    }

    public List<Post> getAll(){
        return postRepository.findAll();
    }

    public List<PostDTO> getAllById(Long id){
        //*return convertPostsToDto(postRepository.findByAccountId(id));
        return postMapper.postsToDto(postRepository.findByAccountId(id));
    }

    /*
    private Post convertDtoToPost(PostDTO postDto){
        return Post.builder()
                .title(postDto.getTitle())
                .body(postDto.getBody())
                .createdAt(postDto.getCreatedAt())
                //*.account(accountService.convertDtoToAccount(postDto.getAccount()))
                .account(accountMapper.toAccount(postDto.getAccount()))
                .build();
    }

    public PostDTO convertPostToDto(Post post){
        PostDTO postDto = new PostDTO();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setId(post.getId());
        postDto.setCreatedAt(post.getCreatedAt());
        postDto.setBody(post.getBody());
        //*postDto.setAccount(accountService.convertAccountToDto(post.getAccount()));
        postDto.setAccount(accountMapper.toDto(post.getAccount()));
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
