package br.com.caiolobo.blogapplication.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.security.Key;
import java.util.*;
import java.util.function.Function;

import org.springframework.core.env.Environment;
@Service
public class JwtService{

    @Autowired
    static Environment env;


    //private static final String SECRET_KEY = env.getProperty("secret.key", "null");
    private static final String SECRET_KEY= "67566B59703373367639792442264528482B4D6251655468576D5A7134743777";


    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    public String generateToken(
            Map<String, Object> extraClaims, UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))      // ajuda a calcular data de expiração;
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))        // qautno tempo o token deve ser válido, dura 1 hora;
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)     // secret key para gerar token
                .compact();     //gera o token
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){     //verifica se o token é valido para usse usuário
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);     //verifica se username extraído é o mesmo do userDetails;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Claims extractAllClaims(String jwtToken){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
