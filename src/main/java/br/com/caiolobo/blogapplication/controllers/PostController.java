package br.com.caiolobo.blogapplication.controllers;

import br.com.caiolobo.blogapplication.models.Post;
import br.com.caiolobo.blogapplication.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping(value = "{id}")
    public ResponseEntity<Optional<Post>> getPost(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(postService.getById(id));
    }
}
