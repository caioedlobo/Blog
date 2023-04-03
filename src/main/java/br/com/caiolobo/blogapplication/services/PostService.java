package br.com.caiolobo.blogapplication.services;

import br.com.caiolobo.blogapplication.dto.PostDTO;
import br.com.caiolobo.blogapplication.exceptions.PostNotFoundException;
import br.com.caiolobo.blogapplication.exceptions.UserNotFoundException;
import br.com.caiolobo.blogapplication.mappers.AccountMapper;
import br.com.caiolobo.blogapplication.mappers.PostMapper;
import br.com.caiolobo.blogapplication.models.entities.Account;
import br.com.caiolobo.blogapplication.models.entities.Post;
import br.com.caiolobo.blogapplication.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class PostService {

    private PostRepository postRepository;
    private AccountMapper accountMapper;
    private PostMapper postMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    public PostService(PostRepository postRepository, AccountMapper accountMapper, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.accountMapper = accountMapper;
        this.postMapper = postMapper;
    }

    public PostDTO save(PostDTO postDto, String emailAccount){
        //Account account = accountRepository.findByEmail(emailAccount);
        Account account = accountService.findByEmail(emailAccount).orElseThrow(UserNotFoundException::new);
        if(postDto.getId() == null){
            postDto.setCreatedAt(String.valueOf(LocalDateTime.now()));
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
