package br.com.caiolobo.blogapplication.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils {

    @Value("67566B59703373367639792442264528482B4D6251655468576D5A7134743777")
    private String secret;

    public UserDetails getUserDetailsFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return new org.springframework.security.core.userdetails.User(
                claims.getSubject(),
                "",
                null
        );
    }
}


