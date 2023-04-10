package br.com.caiolobo.blogapplication.services;

import br.com.caiolobo.blogapplication.auth.AuthenticationRequest;
import br.com.caiolobo.blogapplication.dto.AccountDTO;
import br.com.caiolobo.blogapplication.dto.AccountUpdateDTO;
import br.com.caiolobo.blogapplication.exceptions.AccountAlreadyExistsException;
import br.com.caiolobo.blogapplication.mappers.AccountMapper;
import br.com.caiolobo.blogapplication.models.entities.Account;
import br.com.caiolobo.blogapplication.models.Role;
import br.com.caiolobo.blogapplication.repositories.AccountRepository;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
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
import java.util.Optional;

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
    void itShouldThrowAccountAlreadyExistsExceptionWhenTryingToSaveAccountWithExistingEmail() {
        Account account = new Account(ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, null, null);
        when(accountRepository.findByEmail(EMAIL)).thenReturn(account);

        assertThrows(AccountAlreadyExistsException.class, () -> accountService.save(account));

        verify(accountRepository, times(1)).findByEmail(EMAIL);
        verify(accountRepository, times(0)).save(any(Account.class));
    }

    @Test
    void itShouldGetAllAccounts(){
        Account account = new Account(ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, null, null);
        Account account2 = new Account(2L, "teste2@teste.com", PASSWORD, FIRST_NAME, LAST_NAME, null, null);
        List<Account> accounts = Arrays.asList(account, account2);

        when(accountRepository.findAll()).thenReturn(accounts);

        List<Account> result = accountService.getAll();

        assertNotNull(result);
        assertEquals(accounts, result);

        verify(accountRepository, times(1)).findAll();
    }

    @Test
    void itShouldFindAccountByEmail(){
        Account account = new Account(ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, null, null);
        when(accountRepository.findByEmail(account.getEmail())).thenReturn(account);

        Optional<Account> result = accountService.findByEmail(EMAIL);

        assertTrue(result.isPresent());
        assertEquals(account, result.get());
    }

    @Test
    void itShouldUpdateAccountName(){
        Account account = new Account(ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, null, null);

        AccountUpdateDTO accountUpdateDTO = new AccountUpdateDTO();
        accountUpdateDTO.setFirstName("Jane");
        accountUpdateDTO.setLastName("Doa");

        when(accountRepository.findByEmail(EMAIL)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);

        accountService.updateName(EMAIL, accountUpdateDTO);

        verify(accountRepository, times(1)).findByEmail(EMAIL);
        verify(accountRepository, times(1)).save(account);
        assertEquals(account.getFirstName(), accountUpdateDTO.getFirstName());
        assertEquals(account.getLastName(), accountUpdateDTO.getLastName());
    }

    @Test
    void itShouldUpdateAccountPassword() {
        Account account = new Account(ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, null, null);
        String newPassword = "newPassword";
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail(EMAIL);
        authenticationRequest.setPassword(newPassword);

        when(accountRepository.findByEmail(EMAIL)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);

        accountService.updatePassword(authenticationRequest);

        verify(accountRepository, times(1)).findByEmail(EMAIL);
        verify(accountRepository, times(1)).save(account);

        assertNotEquals(PASSWORD, account.getPassword());
    }
}