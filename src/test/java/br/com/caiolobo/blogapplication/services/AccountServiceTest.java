package br.com.caiolobo.blogapplication.services;

import br.com.caiolobo.blogapplication.dto.AccountDTO;
import br.com.caiolobo.blogapplication.exceptions.UserNotFoundException;
import br.com.caiolobo.blogapplication.models.Account;
import br.com.caiolobo.blogapplication.models.Role;
import br.com.caiolobo.blogapplication.repositories.AccountRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;
    private AccountService underTest;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    void setUp(){
        underTest = new AccountService(accountRepository);
    }

    @Test
    void canSave() {
        // given
        Account account = new Account();
        account.setEmail("email@email.com");
        account.setPassword("password");
        account.setFirstName("Fulano");
        account.setLastName("da Silva");
        account.setRole(Role.USER);

        // mock
        when(accountRepository.save(account)).thenReturn(account);

        // when
        Account savedAccount = underTest.save(account);

        // then
        verify(accountRepository).save(account);
        assertEquals(account, savedAccount);
    }


    @Test
    void canGetAllAccounts() {
        // given
        Account account1 = new Account();
        account1.setEmail("email1@email.com");
        account1.setPassword("password");
        account1.setFirstName("Fulano");
        account1.setLastName("da Silva");
        account1.setRole(Role.USER);

        Account account2 = new Account();
        account2.setEmail("email2@email.com");
        account2.setPassword("password");
        account2.setFirstName("Ciclano");
        account2.setLastName("de Souza");
        account2.setRole(Role.ADMIN);

        List<Account> accounts = Arrays.asList(account1, account2);

        // mock
        when(accountRepository.findAll()).thenReturn(accounts);

        // when
        List<Account> allAccounts = underTest.getAll();

        // then
        verify(accountRepository).findAll();
        assertThat(allAccounts).containsExactlyInAnyOrderElementsOf(accounts);
    }


    @Test
    void canFindByEmail() {
        //given
        String email = "fulano@gmail.com";
        Account account = new Account();
        account.setEmail(email);
        account.setPassword("password");
        account.setFirstName("Fulano");
        account.setLastName("da Silva");
        account.setRole(Role.USER);

        //mock
        when(accountRepository.findByEmail(email)).thenReturn(account);

        //when
        Account accountUnderTest = underTest.findByEmail(email).orElseThrow(UserNotFoundException::new);

        //then
        verify(accountRepository).findByEmail(email);
        assertEquals(account, accountUnderTest);

    }

    @Test
    void canFindById() {
        //given
        Account account = new Account();
        account.setId(1L);
        account.setEmail("fulano@mail.com");
        account.setPassword("password");
        account.setFirstName("Fulano");
        account.setLastName("da Silva");
        account.setRole(Role.USER);
        account.setPosts(Collections.emptyList());

        //mock
        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));

        //when
        AccountDTO savedAccountDTO = underTest.findById(account.getId());
        Account savedAccount = postService.convertDtoToAccount(savedAccountDTO);

        //then
        verify(accountRepository, times(2)).findById(account.getId());
        assertEquals(account.getId(), savedAccount.getId());
    }

}