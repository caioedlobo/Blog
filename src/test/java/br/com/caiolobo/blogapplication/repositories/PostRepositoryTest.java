package br.com.caiolobo.blogapplication.repositories;

import br.com.caiolobo.blogapplication.models.entities.Account;
import br.com.caiolobo.blogapplication.models.entities.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostRepositoryTest {

    @Mock
    private PostRepository postRepository;

    private static final Long ID = 1L;
    private static final String EMAIL = "test@test.com";
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String PASSWORD = "password";
    private static final String TITLE = "Test Post";
    private static final String BODY = "This is a test post.";

    private Account account;
    private Post post;
    private Post post2;

    @BeforeEach
    void setUp(){
        startObjects();
    }

    @Test
    void testFindByAccountId() {
        List<Post> posts = new ArrayList<>();
        posts.add(post);
        posts.add(post2);

        when(postRepository.findAllByAccountId(ID)).thenReturn(posts);

        List<Post> result = postRepository.findAllByAccountId(ID);

        assertEquals(posts, result);
    }

    @Test
    void testFindByTitleContainingIgnoreCaseOrBodyContainingIgnoreCase(){
        String term = "title";

        when(postRepository.findByTitleContainingIgnoreCaseOrBodyContainingIgnoreCase(term, term)).thenReturn(Arrays.asList(post));

        List<Post> result = postRepository.findByTitleContainingIgnoreCaseOrBodyContainingIgnoreCase(term, term);
        System.out.println(result);
        assertNotNull(result);
        assertEquals(post, result.get(0));
        assertEquals(1, result.size());

    }

    private void startObjects(){
        account = new Account(ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, null, null);
        post = new Post(ID, TITLE, BODY, null, account);
        post2 = new Post(2L, "z 2", "body 2", null, account);
    }
}
