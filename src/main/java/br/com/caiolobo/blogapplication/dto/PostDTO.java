package br.com.caiolobo.blogapplication.dto;

import br.com.caiolobo.blogapplication.models.Account;
import br.com.caiolobo.blogapplication.models.View;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    @JsonView({View.Base.class, View.ExcludeAccountFromPost.class})
    private Long id;

    @JsonView({View.Base.class, View.ExcludeAccountFromPost.class})
    @NotEmpty(message = "{campo.title-obrigatorio}")
    private String title;

    @JsonView({View.Base.class, View.ExcludeAccountFromPost.class})
    @NotEmpty(message = "{campo.body-obrigatorio}")
    private String body;

    @JsonView({View.Base.class, View.ExcludeAccountFromPost.class})
    private LocalDateTime createdAt;

    @JsonView(View.Base.class)
    private AccountDTO account;
}
