package br.com.caiolobo.blogapplication.dto;

import br.com.caiolobo.blogapplication.models.Authority;
import br.com.caiolobo.blogapplication.models.Post;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private List<Post> posts;
    private Set<String> authorities = new HashSet<>();
}
