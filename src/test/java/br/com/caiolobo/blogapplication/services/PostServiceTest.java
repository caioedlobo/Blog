package br.com.caiolobo.blogapplication.services;

import br.com.caiolobo.blogapplication.dto.PostDTO;
import br.com.caiolobo.blogapplication.mappers.AccountMapper;
import br.com.caiolobo.blogapplication.models.entities.Account;
import br.com.caiolobo.blogapplication.models.entities.Post;
import br.com.caiolobo.blogapplication.models.Role;
import br.com.caiolobo.blogapplication.repositories.AccountRepository;
import br.com.caiolobo.blogapplication.repositories.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
@Disabled
class PostServiceTest {

    @Mock
    private PostRepository postRepository;
    @InjectMocks
    private PostService underTest;

    private AccountMapper accountMapper;
    @InjectMocks
    private AccountService accountService;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    public void PostService(PostRepository postRepository, AccountService accountService) {
        this.postRepository = postRepository;
        this.accountService = accountService;
    }


    @BeforeEach
    void setUp(){
        underTest = new PostService(postRepository, accountRepository, accountService, accountMapper);
        accountService = new AccountService(accountRepository, passwordEncoder);
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
        //postDto = underTest.convertPostToDto(post);
        when(underTest.convertPostToDto(post)).thenReturn(postDto);

        //then
        assertEquals(1, 1);
        //underTest.save(postDto, account.getEmail());
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
    @Disabled
    void canConvertPostToDto() {
        //given
        Account account = new Account();
        account.setId(1L);
        account.setEmail("fulano@mail.com");
        account.setPassword("password");
        account.setFirstName("Fulano");
        account.setLastName("da Silva");
        account.setRole(Role.USER);
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