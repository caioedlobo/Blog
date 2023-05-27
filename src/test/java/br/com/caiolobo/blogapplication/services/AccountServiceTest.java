package br.com.caiolobo.blogapplication.services;

import br.com.caiolobo.blogapplication.auth.AuthenticationRequest;
import br.com.caiolobo.blogapplication.dto.AccountDTO;
import br.com.caiolobo.blogapplication.dto.AccountUpdateDTO;
import br.com.caiolobo.blogapplication.exceptions.AccountAlreadyExistsException;
import br.com.caiolobo.blogapplication.mappers.AccountMapper;
import br.com.caiolobo.blogapplication.models.PasswordRequest;
import br.com.caiolobo.blogapplication.models.entities.Account;
import br.com.caiolobo.blogapplication.repositories.AccountRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private PostDeletionService postDeletionService;
    @Mock
    private AccountMapper accountMapper;

    private Account account;
    private Account account2;
    private AccountDTO accountDTO;

    @BeforeEach
    void setUp() {
        accountService = new AccountService(accountRepository, passwordEncoder, postDeletionService, accountMapper);
        startAccounts();
    }

    @Test
    void itShouldFindById(){
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(account));
        when(accountMapper.toDto(account)).thenReturn(accountDTO);

        AccountDTO response = accountService.findById(ID);

        assertNotNull(response);
        assertEquals(AccountDTO.class, response.getClass());
        assertEquals(ID, response.getId());
    }

    @Test
    void itShouldSaveAccount() {
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
    void itShouldThrowAccountAlreadyExistsExceptionWhenTryingToSaveAccount() {
        when(accountRepository.findByEmail(EMAIL)).thenReturn(account);

        assertThrows(AccountAlreadyExistsException.class, () -> accountService.save(account));

        verify(accountRepository, times(1)).findByEmail(EMAIL);
        verify(accountRepository, times(0)).save(any(Account.class));
    }

    @Test
    void itShouldGetAllAccounts(){
        List<Account> accounts = Arrays.asList(account, account2);

        when(accountRepository.findAll()).thenReturn(accounts);

        List<Account> result = accountService.getAll();

        assertNotNull(result);
        assertEquals(accounts, result);

        verify(accountRepository, times(1)).findAll();
    }

    @Test
    void itShouldFindAccountByEmail(){
        when(accountRepository.findByEmail(account.getEmail())).thenReturn(account);

        Account result = accountService.findByEmail(EMAIL);

        assertEquals(account, result);
    }

    @Test
    void itShouldUpdateAccountName(){
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
        String encodedPassword = "encodedPassword";
        PasswordRequest passwordRequest = new PasswordRequest("newPassword");

        when(accountRepository.findByEmail(EMAIL)).thenReturn(account);
        when(passwordEncoder.encode(passwordRequest.getPassword())).thenReturn(encodedPassword);
        when(accountRepository.save(account)).thenReturn(account);


        accountService.updatePassword(account.getEmail(), passwordRequest);

        verify(accountRepository, times(1)).findByEmail(EMAIL);
        verify(accountRepository, times(1)).save(account);
        verify(passwordEncoder, times(1)).encode(passwordRequest.getPassword());

        assertNotEquals(PASSWORD, account.getPassword());
        assertEquals(encodedPassword, account.getPassword());
    }

    @Test
    void itShouldGetAccountId(){
        when(accountRepository.findByEmail(EMAIL)).thenReturn(account);
        accountService.getAccountId(account.getEmail());

        assertNotNull(account);
        assertEquals(ID, account.getId());
    }
    @Test
    void itShouldDeleteAccount(){
        when(accountRepository.findByEmail(EMAIL)).thenReturn(account);
        accountService.delete(account.getEmail());

        verify(postDeletionService, times(1)).deleteAllPosts(account.getId());
        verify(accountRepository, times(1)).delete(account);

    }

    private void startAccounts(){
        account = new Account(ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, null, null);
        account2 = new Account(2L, "teste2@teste.com", PASSWORD, FIRST_NAME, LAST_NAME, null, null);
        accountDTO = new AccountDTO(ID, EMAIL, FIRST_NAME, LAST_NAME, null, null);
    }
}