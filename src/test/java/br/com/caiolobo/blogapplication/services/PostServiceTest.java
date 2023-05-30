package br.com.caiolobo.blogapplication.services;

import br.com.caiolobo.blogapplication.dto.AccountDTO;
import br.com.caiolobo.blogapplication.dto.PostDTO;
import br.com.caiolobo.blogapplication.exceptions.PostNotFoundException;
import br.com.caiolobo.blogapplication.mappers.AccountMapper;
import br.com.caiolobo.blogapplication.mappers.PostMapper;
import br.com.caiolobo.blogapplication.models.entities.Account;
import br.com.caiolobo.blogapplication.models.entities.Post;
import br.com.caiolobo.blogapplication.repositories.PostRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

    private Account account;
    private Post post;
    private PostDTO postDTO;
    private AccountDTO accountDTO;
    private Post post2;
    private PostDTO postDTO2;

    @BeforeEach
    void setUp(){
        postService = new PostService(postRepository, accountService, accountMapper, postMapper);
        startPosts();
    }

    @Test
    void itShouldSave(){

        when(accountService.findByEmail(EMAIL)).thenReturn(account);
        when(postMapper.toPost(postDTO, account)).thenReturn(post);
        when(postRepository.save(post)).thenReturn(post);

        PostDTO result = postService.save(postDTO, EMAIL);

        assertNotNull(result);
        assertEquals(post.getId(), result.getId());
        assertEquals(post.getTitle(), result.getTitle());

        verify(accountService, times(1)).findByEmail(EMAIL);
        verify(postMapper, times(1)).toPost(postDTO, account);
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void itShouldFindPostById(){
        when(postMapper.toDto(post)).thenReturn(postDTO);
        when(postRepository.findById(ID)).thenReturn(Optional.of(post));

        PostDTO findPostDTO = postService.findById(ID);

        assertNotNull(findPostDTO);
        assertEquals(postDTO.getId(), findPostDTO.getId());
        assertEquals(postDTO.getTitle(), findPostDTO.getTitle());

        verify(postMapper, times(1)).toDto(post);
        verify(postRepository, times(1)).findById(ID);
    }

    @Test
    void itShouldThrowPostNotFoundExceptionWhenFindingNonExistentPost(){
        when(postRepository.findById(ID)).thenReturn(Optional.empty());
        assertThrows(PostNotFoundException.class, () -> postService.findById(ID));

        verify(postRepository, times(1)).findById(ID);
    }

    @Test
    void itShouldGetAllPosts(){
        List<Post> posts = Arrays.asList(post, post2);
        List<PostDTO> postDTOs = Arrays.asList(postDTO, postDTO2);

        when(postRepository.findAll()).thenReturn(posts);
        when(postMapper.postsToDto(posts)).thenReturn(postDTOs);

        List<PostDTO> result = postService.getAll();

        assertNotNull(result);
        assertEquals(postDTOs.size(), result.size());
        assertEquals(postDTOs.get(0).getId(), result.get(0).getId());
        assertEquals(postDTOs.get(1).getId(), result.get(1).getId());

        verify(postRepository, times(1)).findAll();
        verify(postMapper, times(1)).postsToDto(posts);
    }


    private void startPosts(){
        accountDTO = new AccountDTO(ID, EMAIL, FIRST_NAME, LAST_NAME, null, null);
        postDTO = new PostDTO(ID, TITLE, BODY, null, accountDTO);
        postDTO2 = new PostDTO(2L, TITLE, BODY, null, accountDTO);
        account = new Account(ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, null, null);
        post = new Post(ID, TITLE, BODY, null, account);
        post2 = new Post(2L, TITLE, BODY, null, account);

    }

}