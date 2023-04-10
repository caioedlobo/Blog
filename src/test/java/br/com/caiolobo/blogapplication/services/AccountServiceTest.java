package br.com.caiolobo.blogapplication.services;

import br.com.caiolobo.blogapplication.dto.AccountDTO;
import br.com.caiolobo.blogapplication.exceptions.AccountAlreadyExistsException;
import br.com.caiolobo.blogapplication.mappers.AccountMapper;
import br.com.caiolobo.blogapplication.models.entities.Account;
import br.com.caiolobo.blogapplication.models.Role;
import br.com.caiolobo.blogapplication.repositories.AccountRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Disabled
class AccountServiceTest {

    private static final Long ID = 1L;
    private static final String EMAIL = "test@test.com";
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String PASSWORD = "password";

    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private PostDeletionService postDeletionService;
    @Mock
    private AccountMapper accountMapper;

    @BeforeEach
    void setUp() {
        accountService = new AccountService(accountRepository, passwordEncoder, postDeletionService, accountMapper);
    }

    @Test
    void itShouldSaveAccount() {
        Account account = new Account(ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, null, null);

        when(accountRepository.findByEmail(account.getEmail())).thenReturn(null);
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account result = accountService.save(account);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(account, result);

        verify(accountRepository, times(1)).findByEmail(EMAIL);
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void ItShouldThrowAccountAlreadyExistsExceptionWhenTryingToSaveAccountWithExistingEmail() {
        Account account = new Account(ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, null, null);
        when(accountRepository.findByEmail(EMAIL)).thenReturn(account);

        assertThrows(AccountAlreadyExistsException.class, () -> accountService.save(account));

        verify(accountRepository, times(1)).findByEmail(EMAIL);
        verify(accountRepository, times(0)).save(any(Account.class));
    }
}