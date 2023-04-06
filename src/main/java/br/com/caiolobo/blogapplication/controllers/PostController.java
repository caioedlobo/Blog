package br.com.caiolobo.blogapplication.controllers;

import br.com.caiolobo.blogapplication.services.JwtService;
import br.com.caiolobo.blogapplication.dto.PostDTO;
import br.com.caiolobo.blogapplication.models.View;
import br.com.caiolobo.blogapplication.services.PostService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Tag(name = "Post", description = "This endpoint allows for the creation, reading, updating, and deletion of posts.")
@RestController
@RequestMapping(value = "/api/posts")
public class PostController {


    private final PostService postService;
    private final JwtService jwtService;

    public PostController(PostService postService, JwtService jwtService) {
        this.postService = postService;
        this.jwtService = jwtService;
    }

    @Operation(summary = "Get Post by ID")
    @GetMapping(value = "/{id}")
    @JsonView(View.Base.class)
    public ResponseEntity<PostDTO> getPostById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(postService.findById(id));
    }

    @Operation(summary = "Create Post")
    @PostMapping
    @JsonView(View.Base.class)
    public ResponseEntity<PostDTO> create(@RequestBody @Valid PostDTO postDto, HttpServletRequest request){
       return ResponseEntity.ok(postService.save(postDto, jwtService.getEmailFromRequest(request)));
        //TODO Colocar codigo 201
    }

    @Operation(summary = "Delete Post by ID")
    @DeleteMapping("/{postId}")
    @JsonView(View.Base.class)
    public ResponseEntity delete(HttpServletRequest request, @PathVariable("postId") Long id){
        PostDTO post = postService.findById(id);

        if (isAccountLogged(request, post)){
            postService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @Operation(summary = "Get all Posts by Account ID")
    @GetMapping(value = "/all-posts/{accountId}")
    @JsonView(View.Base.class)
    public ResponseEntity<List<PostDTO>> getAllPostsByUserId(@PathVariable("accountId") Long id){
        return ResponseEntity.ok(postService.getAllById(id));
    }

    @Operation(summary = "Get all Posts")
    @GetMapping(value = "/all-posts")
    @JsonView(View.Base.class)
    public ResponseEntity<List<PostDTO>> getAllPosts(){
        return ResponseEntity.ok(postService.getAll());
    }

    @Operation(summary = "Get all Posts by querying by Title and Body")
    @GetMapping(value = "/all-posts/search")
    @JsonView(View.Base.class)
    public ResponseEntity<List<PostDTO>> getAllPostsByQuery(@RequestParam("query") String query){
        return ResponseEntity.ok(postService.findByQuery(query));
    }

    private boolean isAccountLogged(HttpServletRequest request, PostDTO post) {
        return Objects.equals(jwtService.getIdFromRequest(request), post.getAccount().getId());
    }

}
