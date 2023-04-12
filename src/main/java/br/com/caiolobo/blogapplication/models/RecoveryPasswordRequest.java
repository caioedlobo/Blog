package br.com.caiolobo.blogapplication.models;

import jakarta.validation.constraints.Email;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecoveryPasswordRequest {
    @Email(message = "{campo.email-invalido}")
    private String email;
}
