package br.com.caiolobo.blogapplication.services;

import br.com.caiolobo.blogapplication.dto.PostDTO;
import br.com.caiolobo.blogapplication.models.Post;
import br.com.caiolobo.blogapplication.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post save(PostDTO postDto){
        if(postDto.getId() == null){
            postDto.setCreatedAt(LocalDateTime.now());
        }
        return postRepository.save(convertDtoToPost(postDto));
    }

    public Post getById(Long id){
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Teste"));
        //return convertPostToDto(post);
        return post;
    }

    public List<Post> getAll(){
        return postRepository.findAll();
    }

    private Post convertDtoToPost(PostDTO postDto){
        return Post.builder()
                .title(postDto.getTitle())
                .body(postDto.getBody())
                .createdAt(postDto.getCreatedAt())
                .account(postDto.getAccount())
                .build();
    }

    private PostDTO convertPostToDto(Post post){
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .createdAt(post.getCreatedAt())
                .account(post.getAccount())
                .build();
    }
}
