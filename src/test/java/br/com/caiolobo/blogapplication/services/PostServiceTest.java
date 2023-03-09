package br.com.caiolobo.blogapplication.services;

import br.com.caiolobo.blogapplication.dto.PostDTO;
import br.com.caiolobo.blogapplication.models.Account;
import br.com.caiolobo.blogapplication.models.Post;
import br.com.caiolobo.blogapplication.models.Role;
import br.com.caiolobo.blogapplication.repositories.AccountRepository;
import br.com.caiolobo.blogapplication.repositories.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    private PostService underTest;

    @Autowired
    private AccountService accountService;
    @Mock
    private AccountRepository accountRepository;

    public void PostService(PostRepository postRepository, AccountService accountService) {
        this.postRepository = postRepository;
        this.accountService = accountService;
    }

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void setUp(){
        underTest = new PostService(postRepository, accountRepository);
        accountService = new AccountService(accountRepository);
    }

    @Test
    @Disabled
    void canSave() {
        //given
        Account account = new Account();
        account.setId(1L);
        account.setEmail("fulano@mail.com");
        account.setPassword("password");
        account.setFirstName("Fulano");
        account.setLastName("da Silva");
        account.setRole(Role.USER);

        Post post = new Post();
        post.setTitle("Título");
        post.setBody("Corpo do texto");
        post.setAccount(account);

        PostDTO postDto =  new PostDTO();
        System.out.println(post.getAccount());
        //when
        postDto = underTest.convertPostToDto(post);

        //then
        underTest.save(postDto, account.getEmail());
    }

    @Test
    @Disabled
    void getById() {
    }

    @Test
    @Disabled
    void getAll() {
    }

    @Test
    void canConvertPostToDto() {
        //given
        Account account = new Account();
        account.setId(1L);
        account.setEmail("fulano@mail.com");
        account.setPassword("password");
        account.setFirstName("Fulano");
        account.setLastName("da Silva");
        account.setRole(Role.USER);
        System.out.println(accountService.hello());
        Post post = new Post();
        post.setId(1L);
        post.setTitle("Título");
        post.setBody("Corpo do texto");
        post.setAccount(account);
        //PostDTO postDto = new PostDTO();
        //when(underTest.convertPostToDto(post)).thenReturn(postDto);
        PostDTO postDto = underTest.convertPostToDto(post);
        //System.out.println(accountService.convertAccountToDto(account));
        //when
        //PostDTO postDto = underTest.convertPostToDto(post);

        //then
        //verify
        assertEquals(postDto.getId(), post.getId());

    }

    @Test
    @Disabled
    void convertPostsToDto() {
    }
}