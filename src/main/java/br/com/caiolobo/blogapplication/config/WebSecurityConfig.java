package br.com.caiolobo.blogapplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
/*
    import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.web.SecurityFilterChain;

    //@EnableWebSecurity
    //@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
    public class WebSecurityConfig {

        private static final String[] WHITELIST = {
                "register",
                "/"
        };

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
            http
                    .authorizeRequests()
                    .antMatchers(WHITELIST).permitAll()
                    .antMatchers(HttpMethod.GET, "/posts/*").permitAll()        //permite método GET nessa rota
                    .anyRequest().authenticated();
            return http.build();
        }
    }
*/