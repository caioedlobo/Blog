package br.com.caiolobo.blogapplication.dto;

import br.com.caiolobo.blogapplication.models.View;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    @JsonView({View.Base.class, View.ExcludeAccountFromPost.class})
    private Long id;

    @JsonView({View.Base.class, View.ExcludeAccountFromPost.class})
    private String email;

    @JsonView({View.Base.class, View.ExcludeAccountFromPost.class})
    private String firstName;

    @JsonView({View.Base.class, View.ExcludeAccountFromPost.class})
    private String lastName;

    @ArraySchema(schema = @Schema(implementation = String.class))
    @JsonView(View.ExcludeAccountFromPost.class)
    private List<PostDTO> posts;

    private Set<String> authorities = new HashSet<>();
}