package br.com.caiolobo.blogapplication.repositories;

import br.com.caiolobo.blogapplication.models.entities.Account;
import br.com.caiolobo.blogapplication.models.entities.Post;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostRepositoryTest {

    @Mock
    private PostRepository postRepository;

    @Test
    void testFindByAccountId() {
        //given
        Long id = 1L;
        Account account = new Account();
        account.setId(id);

        Post post1 = new Post();
        post1.setId(1L);
        post1.setTitle("Title 1");
        post1.setBody("Body 1");
        post1.setAccount(account);

        Post post2 = new Post();
        post2.setId(2L);
        post2.setTitle("Title 2");
        post2.setBody("Body 2");
        post2.setAccount(account);

        List<Post> posts = new ArrayList<>();
        posts.add(post1);
        posts.add(post2);

        when(postRepository.findByAccountId(id)).thenReturn(posts);

        //when
        List<Post> result = postRepository.findByAccountId(id);

        //then
        assertEquals(posts, result);
    }

    @Test
    void testFindByTitleContainingIgnoreCaseOrBodyContainingIgnoreCase(){
        //given
        String term = "title";
        Long id = 1L;

        Account account = new Account();
        account.setId(id);

        Post post1 = new Post();
        post1.setId(1L);
        post1.setTitle("Title 1");
        post1.setBody("Body 1");
        post1.setAccount(account);

        Post post2 = new Post();
        post2.setId(2L);
        post2.setTitle("z 2");
        post2.setBody("Body 2");
        post2.setAccount(account);

        List<Post> posts = new ArrayList<>();
        posts.add(post1);
        posts.add(post2);

        //when
        when(postRepository.findByTitleContainingIgnoreCaseOrBodyContainingIgnoreCase(term, term)).thenReturn(posts);
        List<Post> result = postRepository.findByTitleContainingIgnoreCaseOrBodyContainingIgnoreCase(term, term);

        //then
        assertEquals(posts.size(), result.size());
        assertEquals(posts, result);
    }
}
