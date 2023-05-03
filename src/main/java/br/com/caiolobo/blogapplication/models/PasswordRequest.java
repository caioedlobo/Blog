package br.com.caiolobo.blogapplication.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRequest {

    @NotEmpty(message = "{campo.password-obrigatorio}")
    private String password;
}
