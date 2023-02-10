package br.com.caiolobo.blogapplication.config;

import br.com.caiolobo.blogapplication.models.Account;
import br.com.caiolobo.blogapplication.models.Post;
import br.com.caiolobo.blogapplication.services.AccountService;
import br.com.caiolobo.blogapplication.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @Override
    public void run(String... args) throws Exception {
        List<Post> posts = postService.getAll();
        List<Account> accounts = accountService.getAll();

        if (accounts.isEmpty()) {
            Account account1 = new Account();
            account1.setEmail("fulano@gmail.com");
            account1.setPassword("password");
            account1.setFirstName("Fulano");
            account1.setLastName("da Silva");

            Account account2 = new Account();
            account2.setEmail("fulano2@gmail.com");
            account2.setPassword("password2");
            account2.setFirstName("Fulano 2");
            account2.setLastName("da Silva 2");

            accountService.save(account1);
            accountService.save(account2);

            Post post1 = new Post();
            post1.setTitle("Título do post 1");
            post1.setBody("Corpo do texto do post 1");
            post1.setAccount(account1);

            Post post2 = new Post();
            post2.setTitle("Título do post 2");
            post2.setBody("Corpo do texto do post 2");
            post2.setAccount(account2);

            postService.save(post1);
            postService.save(post2);
    }


    }
}
