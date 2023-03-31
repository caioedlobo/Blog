package br.com.caiolobo.blogapplication.services;

import br.com.caiolobo.blogapplication.models.entities.Post;
import br.com.caiolobo.blogapplication.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostDeletionService {      // classe criada para evitar a Circylar Dependency

    private final PostRepository postRepository;

    public PostDeletionService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void deleteAllPosts(Long id) {
        List<Post> posts = postRepository.findByAccountId(id);
        for(Post post: posts){
            postRepository.delete(post);
        }
    }
}
