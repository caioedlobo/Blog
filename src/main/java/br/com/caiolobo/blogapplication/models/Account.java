package br.com.caiolobo.blogapplication.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Nullable;
import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
@Entity
public class Account implements UserDetails {

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
    @ToString.Exclude
    private List<Post> posts = new ArrayList<>();

    //@ManyToMany(fetch = FetchType.EAGER)
    //@JoinTable(name = "account_authority", joinColumns = {@JoinColumn(name = "account_id", referencedColumnName = "id")},
    //inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
    //private Set<Authority> authorities = new HashSet<>();


    //Métodos para implementar do UserDetails
    @Enumerated(EnumType.STRING)
    private Role role;


    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
