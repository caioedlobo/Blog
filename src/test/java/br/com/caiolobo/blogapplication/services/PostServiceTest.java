package br.com.caiolobo.blogapplication.services;

import br.com.caiolobo.blogapplication.dto.AccountDTO;
import br.com.caiolobo.blogapplication.dto.PostDTO;
import br.com.caiolobo.blogapplication.exceptions.AccountNotFoundException;
import br.com.caiolobo.blogapplication.mappers.AccountMapper;
import br.com.caiolobo.blogapplication.mappers.PostMapper;
import br.com.caiolobo.blogapplication.models.entities.Account;
import br.com.caiolobo.blogapplication.models.entities.Post;
import br.com.caiolobo.blogapplication.models.Role;
import br.com.caiolobo.blogapplication.repositories.AccountRepository;
import br.com.caiolobo.blogapplication.repositories.PostRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    private static final Long ID = 1L;
    private static final String EMAIL = "test@test.com";
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String PASSWORD = "password";
    private static final String TITLE = "Test Post";
    private static final String BODY = "This is a test post.";

    @Mock
    private PostRepository postRepository;
    @Mock
    private AccountMapper accountMapper;
    @Mock
    private PostMapper postMapper;
    @Mock
    private AccountService accountService;
    @InjectMocks
    private PostService postService;

    @BeforeEach
    void setUp(){
        postService = new PostService(postRepository, accountService, accountMapper, postMapper);
    }

    @Test
    void itShouldSave(){
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle(TITLE);
        postDTO.setBody(BODY);
        Account account = new Account(ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, null, null);
        Post post = new Post(ID, TITLE, BODY, null, account);

        when(accountService.findByEmail(EMAIL)).thenReturn(Optional.of(account));
        when(postMapper.toPost(postDTO, account)).thenReturn(post);
        when(postRepository.save(any(Post.class))).thenReturn(post);

        PostDTO result = postService.save(postDTO, EMAIL);

        assertNotNull(result);
        assertEquals(post.getId(), result.getId());
        assertEquals(post.getTitle(), result.getTitle());

        verify(accountService, times(1)).findByEmail(EMAIL);
        verify(postMapper, times(1)).toPost(postDTO, account);
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void itShouldThrowAccountNotFoundExceptionWhenSavingPostWithNonExistentAccount(){
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle(TITLE);
        postDTO.setBody(BODY);

        when(accountService.findByEmail(EMAIL)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> postService.save(postDTO, EMAIL));

        verify(accountService, times(1)).findByEmail(EMAIL);
        verify(postRepository, times(0)).save(any(Post.class));
    }

    @Test
    public void itShouldFindPostById(){
        PostDTO postDTO = new PostDTO();
        postDTO.setId(ID);
        postDTO.setTitle(TITLE);
        postDTO.setBody(BODY);
        Account account = new Account(ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, null, null);
        Post post = new Post(ID, TITLE, BODY, null, account);

        when(postMapper.toDto(post)).thenReturn(postDTO);
        when(postRepository.findById(ID)).thenReturn(Optional.of(post));

        PostDTO findPostDTO = postService.findById(ID);

        assertNotNull(findPostDTO);
        assertEquals(postDTO.getId(), findPostDTO.getId());
        assertEquals(postDTO.getTitle(), findPostDTO.getTitle());

        verify(postMapper, times(1)).toDto(post);
        verify(postRepository, times(1)).findById(ID);
    }

}