package br.com.caiolobo.blogapplication.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @NotEmpty(message = "{campo.email-obrigatorio}")
    @Email(message = "{campo.email-invalido}")
    private String email;
    @NotEmpty(message = "{campo.password-obrigatorio}")
    private String password;
}
