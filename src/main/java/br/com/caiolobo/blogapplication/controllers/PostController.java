package br.com.caiolobo.blogapplication.controllers;

import br.com.caiolobo.blogapplication.config.JwtService;
import br.com.caiolobo.blogapplication.dto.PostDTO;
import br.com.caiolobo.blogapplication.models.View;
import br.com.caiolobo.blogapplication.services.PostService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Post", description = "This endpoint allows for the creation, reading, updating, and deletion of posts.")
@RestController
@RequestMapping(value = "/api/posts")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private JwtService jwtService;

    @Operation(summary = "Get Post by ID")
    @GetMapping(value = "{id}")
    @JsonView(View.Base.class)
    public ResponseEntity<PostDTO> getPost(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(postService.getById(id));
    }

    @PostMapping
    @JsonView(View.Base.class)
    public ResponseEntity<PostDTO> createPost(@RequestBody @Valid PostDTO postDto, HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7); // remove o prefixo "Bearer "
        String emailAccount = jwtService.extractUsername(token);

       return ResponseEntity.ok(postService.save(postDto, emailAccount));
    }

}
