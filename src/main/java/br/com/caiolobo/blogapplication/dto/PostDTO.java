package br.com.caiolobo.blogapplication.dto;

import br.com.caiolobo.blogapplication.models.Account;
import br.com.caiolobo.blogapplication.models.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    @JsonView({View.Base.class, View.ExcludeAccountfromPost.class})
    private Long id;

    @JsonView({View.Base.class, View.ExcludeAccountfromPost.class})
    private String title;

    @JsonView({View.Base.class, View.ExcludeAccountfromPost.class})
    private String body;

    @JsonView({View.Base.class, View.ExcludeAccountfromPost.class})
    private LocalDateTime createdAt;

    @JsonView(View.Base.class)
    private AccountDTO account;
}
