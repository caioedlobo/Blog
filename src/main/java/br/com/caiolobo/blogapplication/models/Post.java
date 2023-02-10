package br.com.caiolobo.blogapplication.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;
    @Column(columnDefinition = "TEXT")
    private String body;

    private LocalDateTime createdAt;
}
