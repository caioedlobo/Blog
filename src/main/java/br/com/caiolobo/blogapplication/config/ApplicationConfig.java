package br.com.caiolobo.blogapplication.config;

import br.com.caiolobo.blogapplication.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final AccountRepository accountRepository;

    @Bean
    public UserDetailsService userDetailsService() throws UsernameNotFoundException{    //loadUserByUsername
        return username -> accountRepository.findByEmail(username);
    }
}
