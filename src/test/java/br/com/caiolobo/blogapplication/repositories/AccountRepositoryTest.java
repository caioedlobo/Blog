package br.com.caiolobo.blogapplication.repositories;

import br.com.caiolobo.blogapplication.models.Account;
import br.com.caiolobo.blogapplication.models.Role;
import br.com.caiolobo.blogapplication.services.AccountService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository underTest;

    @Autowired
    private AccountService accountService;

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }



    @Test
    void itShouldFindAccountByEmail() {

        //given
        String email = "mail@example.com";

        Account account1 = new Account();
        account1.setEmail(email);
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

    @Test
    void itShouldNotFindAccountByEmail() {

        //given
        String email = "mail@example.com";

        //when
        Account findAccount = underTest.findByEmail(email);

        //then
        assertThat(findAccount).isNull();
    }
}