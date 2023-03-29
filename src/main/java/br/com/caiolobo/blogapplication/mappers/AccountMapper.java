package br.com.caiolobo.blogapplication.mappers;

import br.com.caiolobo.blogapplication.dto.AccountDTO;
import br.com.caiolobo.blogapplication.models.entities.Account;
import br.com.caiolobo.blogapplication.services.AccountService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountMapper {

    private AccountService accountService;

    public AccountMapper(AccountService accountService) {
        this.accountService = accountService;
    }

    public AccountDTO toDTO(Account account){
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setFirstName(account.getFirstName());
        accountDTO.setLastName(account.getLastName());
        accountDTO.setEmail(account.getEmail());
        //accountDTO.setPosts();
        return accountDTO;
    }

    public Account toEntity(AccountDTO accountDTO){
        return accountService.findById(accountDTO.getId());
    }

    public List<AccountDTO> toDTOList(List<Account> accounts) {
        return accounts.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Account> toEntityList(List<AccountDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
