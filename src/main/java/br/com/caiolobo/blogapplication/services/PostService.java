package br.com.caiolobo.blogapplication.services;

import br.com.caiolobo.blogapplication.dto.PostDTO;
import br.com.caiolobo.blogapplication.exceptions.PostNotFoundException;
import br.com.caiolobo.blogapplication.mappers.AccountMapper;
import br.com.caiolobo.blogapplication.mappers.PostMapper;
import br.com.caiolobo.blogapplication.models.entities.Account;
import br.com.caiolobo.blogapplication.models.entities.Post;
import br.com.caiolobo.blogapplication.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class PostService {

    private final PostRepository postRepository;
    private final AccountMapper accountMapper;
    private final PostMapper postMapper;
    private final AccountService accountService;

    public PostService(PostRepository postRepository, AccountService accountService, AccountMapper accountMapper, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.accountService = accountService;
        this.accountMapper = accountMapper;
        this.postMapper = postMapper;
    }

    public PostDTO save(PostDTO postDto, String emailAccount){
        Account account = accountService.findByEmail(emailAccount);
        if(postDto.getId() == null){
            postDto.setCreatedAt(String.valueOf(LocalDateTime.now()));

            postDto.setAccount(accountMapper.toDto(account));
        }
        Post post = postRepository.save(postMapper.toPost(postDto, account));
        postDto.setId(post.getId());
        return postDto;
    }

    public PostDTO findById(Long id){
        return postMapper.toDto(postRepository.findById(id).orElseThrow(PostNotFoundException::new));
    }

    public List<PostDTO> getAll(){return postMapper.postsToDto(postRepository.findAll());}

    public List<PostDTO> findByQuery(String query){
        return postMapper.postsToDto(postRepository.findByTitleContainingIgnoreCaseOrBodyContainingIgnoreCase(query, query));
    }



    public List<PostDTO> getAllById(Long id){
        //*return convertPostsToDto(postRepository.findByAccountId(id));
        return postMapper.postsToDto(postRepository.findByAccountId(id));
    }

    private Set<String> addAccountAuthoritiesToDto(Account account){
        Set<String> authorities = new HashSet<>();
        account.getAuthorities().stream()
                .map(authority -> authorities.add(String.valueOf(authority)))
                .collect(Collectors.toSet());
        return authorities;
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }
}
