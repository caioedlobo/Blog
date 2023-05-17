package br.com.caiolobo.blogapplication.mappers;

import br.com.caiolobo.blogapplication.dto.AccountDTO;
import br.com.caiolobo.blogapplication.dto.PostDTO;
import br.com.caiolobo.blogapplication.models.PageWrapper;
import br.com.caiolobo.blogapplication.models.entities.Account;
import br.com.caiolobo.blogapplication.models.entities.Post;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostAccountMapper{

    public PostDTO postToDto(Post post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setBody(post.getBody());
        dto.setCreatedAt(String.valueOf(post.getCreatedAt()));
        dto.setAccount(accountToDto(post.getAccount()));
        return dto;
    }

    public Post dtoToPost(PostDTO dto, Account account) {
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setBody(dto.getBody());
        post.setCreatedAt(LocalDateTime.parse(dto.getCreatedAt()));
        //post.setAccount(dtoToAccount(dto.getAccount()));
        post.setAccount(account);
        return post;
    }

    public AccountDTO accountToDto(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setId(account.getId());
        dto.setFirstName(account.getFirstName());
        dto.setLastName(account.getLastName());
        dto.setEmail(account.getEmail());
        //dto.setPosts(postsToDto(account.getPosts()));
        //dto.setPosts(posts);
        return dto;
    }

    public Account dtoToAccount(AccountDTO dto,  List<Post> posts) {
        Account account = new Account();
        account.setFirstName(dto.getFirstName());
        account.setLastName(dto.getLastName());
        account.setEmail(dto.getEmail());
        //account.setPosts(dtoToPosts(dto.getPosts()));
        account.setPosts(posts);
        return account;

    }

    public List<Post> dtoToPosts(List<PostDTO> dtos, Account account) {
        return dtos.stream().map(dto -> dtoToPost(dto, account)).collect(Collectors.toList());
    }


    public List<PostDTO> postsToDto(List<Post> dtos) {
        return dtos.stream().map(this::postToDto).collect(Collectors.toList());
    }
    public Page<PostDTO> postsPageToDto(Page<Post> posts) {
        return posts.map(this::postToDto);
    }

    public PageWrapper<PostDTO> postsPageWrapperToDto(Page<Post> posts) {
        List<PostDTO> content = posts.getContent().stream().map(this::postToDto).collect(Collectors.toList());

        return new PageWrapper<>(content, posts.getPageable(), posts.getTotalElements());
    }
    /*
    public PostDTO postToDtoWithoutAccount(Post post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setBody(post.getBody());
        return dto;
    }

    public Post dtoToPostWithoutAccount(PostDTO dto) {
        Post post = new Post();
        //post.setId(dto.getId());
        post.setTitle(dto.getTitle());
        post.setBody(dto.getBody());
        post.setAccount(dtoToAccount(dto.getAccount()));
        return post;
    }

    public List<Post> dtoToPosts(List<PostDTO> dtos) {
        return dtos.stream().map(this::dtoToPostWithoutAccount).collect(Collectors.toList());
    }


    public List<PostDTO> postsToDto(List<Post> dtos) {
        return dtos.stream().map(this::postToDtoWithoutAccount).collect(Collectors.toList());
    }*/
}
