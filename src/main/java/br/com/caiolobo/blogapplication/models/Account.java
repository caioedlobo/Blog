package br.com.caiolobo.blogapplication.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Campo email é obrigatório")
    private String email;

    @NotEmpty(message = "Campo senha é obrigatório")
    private String password;

    @NotEmpty(message = "Campo primeiro nome é obrigatório")
    private String firstName;

    @NotEmpty(message = "Campo último nome é obrigatório")
    private String lastName;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<Post> posts;
}
