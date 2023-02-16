package br.com.caiolobo.blogapplication.controllers;

import br.com.caiolobo.blogapplication.dto.PostDTO;
import br.com.caiolobo.blogapplication.models.Post;
import br.com.caiolobo.blogapplication.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping(value = "{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(postService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostDTO postDto){
        return ResponseEntity.ok(postService.save(postDto));
    }
}
