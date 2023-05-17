package br.com.caiolobo.blogapplication.mappers;

import br.com.caiolobo.blogapplication.dto.PostDTO;
import br.com.caiolobo.blogapplication.models.PageWrapper;
import br.com.caiolobo.blogapplication.models.entities.Account;
import br.com.caiolobo.blogapplication.models.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class PostMapper {
    private final PostAccountMapper postAccountMapper;

    public PostMapper(PostAccountMapper postAccountMapper) {
        this.postAccountMapper = postAccountMapper;
    }

    public PostDTO toDto(Post post){
        return postAccountMapper.postToDto(post);
    }
    public Post toPost(PostDTO postDto, Account account){
        return postAccountMapper.dtoToPost(postDto, account);
    }

    public List<Post> dtoToPosts(List<PostDTO> postsDto, Account account){
        return postAccountMapper.dtoToPosts(postsDto, account);
    }
    public List<PostDTO> postsToDto(List<Post> posts){
        return postAccountMapper.postsToDto(posts);
    }
    public Page<PostDTO> postsPageToDto(Page<Post> posts){
        return postAccountMapper.postsPageToDto(posts);
    }
    public PageWrapper<PostDTO> postsPageWrapperToDto(Page<Post> posts){
        return postAccountMapper.postsPageWrapperToDto(posts);
    }

}
