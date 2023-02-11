package br.com.caiolobo.blogapplication.dto;

import br.com.caiolobo.blogapplication.models.Account;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private Long id;

    private String title;

    private String body;

    private LocalDateTime createdAt;

    private Account account;
}
