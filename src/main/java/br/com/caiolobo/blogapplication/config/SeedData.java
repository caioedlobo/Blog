package br.com.caiolobo.blogapplication.config;

import br.com.caiolobo.blogapplication.dto.AccountDTO;
import br.com.caiolobo.blogapplication.dto.PostDTO;
import br.com.caiolobo.blogapplication.models.Account;
import br.com.caiolobo.blogapplication.models.Authority;
import br.com.caiolobo.blogapplication.models.Post;
import br.com.caiolobo.blogapplication.models.Role;
import br.com.caiolobo.blogapplication.repositories.AuthorityRepository;
import br.com.caiolobo.blogapplication.services.AccountService;
import br.com.caiolobo.blogapplication.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    private PostService postService;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private AccountService accountService;

    @Override
    public void run(String... args) throws Exception {
        List<Post> posts = postService.getAll();
        List<Account> accounts = accountService.getAll();

        if (accounts.isEmpty()) {

            /*Authority user = new Authority();
            user.setName("ROLE_USER");
            authorityRepository.save(user);

            Authority admin = new Authority();
            admin.setName("ROLE_ADMIN");
            authorityRepository.save(admin);
            */
            Account account1 = new Account();
            account1.setEmail("fulano@gmail.com");
            account1.setPassword("password");
            account1.setFirstName("Fulano");
            account1.setLastName("da Silva");
            account1.setRole(Role.USER);

            Set<Authority> authorities1 = new HashSet<>();
            authorityRepository.findById("ROLE_USER").ifPresent(authorities1::add);
            account1.setAuthorities(authorities1);


            Account account2 = new Account();
            account2.setEmail("fulano2@gmail.com");
            account2.setPassword("password2");
            account2.setFirstName("Fulano 2");
            account2.setLastName("da Silva 2");
            account2.setRole(Role.USER);

            Set<Authority> authorities2 = new HashSet<>();
            authorityRepository.findById("ROLE_USER").ifPresent(authorities2::add);
            authorityRepository.findById("ROLE_ADMIN").ifPresent(authorities2::add);
            account1.setAuthorities(authorities2);


            accountService.save(account1);
            accountService.save(account2);

            PostDTO post1 = new PostDTO();
            post1.setTitle("Título do post 1");
            post1.setBody("Corpo do texto do post 1");
            post1.setAccount(convertAccountToDto(account1));

            PostDTO post2 = new PostDTO();
            post2.setTitle("Título do post 2");
            post2.setBody("Corpo do texto do post 2");
            post2.setAccount(convertAccountToDto(account2));

            postService.save(post1);
            postService.save(post2);
    }

    }
    private AccountDTO convertAccountToDto(Account account){
        return AccountDTO.builder()
                .id(account.getId())
                .email(account.getUsername())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .authorities(addAccountAuthoritiesToDto(account))
                .posts(account.getPosts())
                .build();

    }
    private Set<String> addAccountAuthoritiesToDto(Account account){
        Set<String> authorities = new HashSet<>();
        account.getAuthorities().stream()
                .map(authority -> authorities.add(String.valueOf(authority)))
                .collect(Collectors.toSet());
        return authorities;
    }
}
