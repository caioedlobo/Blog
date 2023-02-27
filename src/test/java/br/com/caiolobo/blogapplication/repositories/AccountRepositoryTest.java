package br.com.caiolobo.blogapplication.repositories;

import br.com.caiolobo.blogapplication.models.Account;
import br.com.caiolobo.blogapplication.models.Authority;
import br.com.caiolobo.blogapplication.models.Role;
import br.com.caiolobo.blogapplication.services.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository underTest;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Test
    void itShouldFindByEmail() {

        //given
        String email = "mail@example.com";

        Account account1 = new Account();
        account1.setEmail("mail@example.com");
        account1.setPassword("password");
        account1.setFirstName("Fulano");
        account1.setLastName("da Silva");
        account1.setRole(Role.USER);

        underTest.save(account1);

        //when
        Account findAccount = underTest.findByEmail(email);

        //then
        assertThat(account1.getEmail()).isEqualTo(findAccount.getEmail());
    }
}