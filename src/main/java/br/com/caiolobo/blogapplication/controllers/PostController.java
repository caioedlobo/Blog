package br.com.caiolobo.blogapplication.controllers;

import br.com.caiolobo.blogapplication.config.JwtService;
import br.com.caiolobo.blogapplication.dto.PostDTO;
import br.com.caiolobo.blogapplication.models.Post;
import br.com.caiolobo.blogapplication.services.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/posts")
public class PostController {

    @Autowired
    private PostService postService;



    @Autowired
    private JwtService jwtService;

    @GetMapping(value = "{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(postService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostDTO postDto, HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7); // remove o prefixo "Bearer "
        String emailAccount = jwtService.extractUsername(token);

       return ResponseEntity.ok(postService.save(postDto, emailAccount));
    }
}
