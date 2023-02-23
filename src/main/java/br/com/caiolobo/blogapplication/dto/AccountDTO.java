package br.com.caiolobo.blogapplication.dto;

import br.com.caiolobo.blogapplication.models.Authority;
import br.com.caiolobo.blogapplication.models.Post;
import br.com.caiolobo.blogapplication.models.View;
import com.fasterxml.jackson.annotation.JsonView;
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
    @JsonView({View.Base.class, View.ExcludeAccountfromPost.class})
    private Long id;
    @JsonView({View.Base.class, View.ExcludeAccountfromPost.class})
    private String email;
    @JsonView({View.Base.class, View.ExcludeAccountfromPost.class})
    private String firstName;
    @JsonView({View.Base.class, View.ExcludeAccountfromPost.class})
    private String lastName;
    @JsonView(View.ExcludeAccountfromPost.class)
    private List<PostDTO> posts;
    private Set<String> authorities = new HashSet<>();
}
