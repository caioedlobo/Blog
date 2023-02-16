package br.com.caiolobo.blogapplication.dto;

import lombok.*;

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

    private AccountDTO account;
}
