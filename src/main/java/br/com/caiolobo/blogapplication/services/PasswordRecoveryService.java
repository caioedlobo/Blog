package br.com.caiolobo.blogapplication.services;

import br.com.caiolobo.blogapplication.auth.AuthenticationRequest;
import br.com.caiolobo.blogapplication.exceptions.TokenExpiredException;
import br.com.caiolobo.blogapplication.models.PasswordTokenPublicData;
import br.com.caiolobo.blogapplication.models.RecoveryPasswordRequest;
import br.com.caiolobo.blogapplication.models.entities.Account;
import br.com.caiolobo.blogapplication.repositories.AccountRepository;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.SecureRandomFactoryBean;
import org.springframework.security.core.token.Token;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Service
public class PasswordRecoveryService {
    private final KeyBasedPersistenceTokenService tokenService = new KeyBasedPersistenceTokenService();
    private final AccountRepository accountRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public PasswordRecoveryService(AccountRepository accountRepository, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    public String generateToken(RecoveryPasswordRequest request) throws Exception{
        Account account = accountRepository.findByEmail(request.getEmail());
        tokenService.setServerSecret(account.getPassword());
        tokenService.setServerInteger(16);
        tokenService.setSecureRandom(new SecureRandomFactoryBean().getObject());

        Token token = tokenService.allocateToken(account.getEmail());
        emailService.sendRecoveryMessage(account.getEmail(), String.valueOf(token.getKey()));
        return String.valueOf(token.getKey());
    }

    public void changePassword(String newPassword, String token){
        PasswordTokenPublicData publicData = readPublicData(token);
        if (Boolean.TRUE.equals(isTokenExpired(publicData))){
            throw new TokenExpiredException();
        }
        tokenService.verifyToken(token);

        Account account = accountRepository.findByEmail(publicData.getEmail());
        account.setPassword(passwordEncoder.encode(newPassword));
        accountRepository.save(account);
    }


    private Boolean isTokenExpired(PasswordTokenPublicData publicData){
        Instant createdAt = new Date(publicData.getCreateAtTimestamp()).toInstant();
        Instant now = new Date().toInstant();
        return createdAt.plus(Duration.ofDays(1)).isBefore(now);
    }

    private PasswordTokenPublicData readPublicData(String token){
        String rawTokenDecoded = new String(Base64.getDecoder().decode(token));
        String[] tokenParts = rawTokenDecoded.split(":");
        Long timestamp = Long.parseLong(tokenParts[0]);
        String email = tokenParts[2];
        return new PasswordTokenPublicData(email, timestamp);
    }

}
