package br.com.caiolobo.blogapplication.repositories;

import br.com.caiolobo.blogapplication.models.entities.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountRepositoryTest {

    @Mock
    private AccountRepository accountRepository;

    @Test
    void findByEmail() {
        String email = "fulano@gmail.com";
        Account account = new Account();
        account.setEmail(email);

        when(accountRepository.findByEmail(email)).thenReturn(account);

        Account result = accountRepository.findByEmail(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }
}