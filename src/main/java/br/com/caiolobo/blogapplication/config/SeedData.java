package br.com.caiolobo.blogapplication.config;

import br.com.caiolobo.blogapplication.models.Post;
import br.com.caiolobo.blogapplication.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    private PostService postService;

    @Override
    public void run(String... args) throws Exception {
        List<Post> posts = postService.getAll();

        if(posts.isEmpty()){
            Post post1 = new Post();
            post1.setTitle("Título do post 1");
            post1.setBody("Corpo do texto do post 1");

            Post post2 = new Post();
            post2.setTitle("Título do post 2");
            post2.setBody("Corpo do texto do post 2");

            postService.save(post1);
            postService.save(post2);

        }
    }
}
