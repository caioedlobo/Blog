package br.com.caiolobo.blogapplication.mappers;

import br.com.caiolobo.blogapplication.dto.AccountDTO;
import br.com.caiolobo.blogapplication.models.entities.Account;
import br.com.caiolobo.blogapplication.models.entities.Post;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountMapper {
    private final PostAccountMapper postAccountMapper;
    public AccountMapper(PostAccountMapper postAccountMapper) {
        this.postAccountMapper = postAccountMapper;
    }

    public AccountDTO toDto(Account account) {
        return postAccountMapper.accountToDto(account);
    }

    public Account toAccount(AccountDTO dto, List<Post> posts) { return postAccountMapper.dtoToAccount(dto, posts); }
}
