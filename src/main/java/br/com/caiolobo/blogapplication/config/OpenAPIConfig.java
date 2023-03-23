package br.com.caiolobo.blogapplication.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("API do Blog usando Spring Boot")
                        .description("Esta API REST fornece endpoints para acessar e manipular recursos relacionados a API do Blog. Ela segue o padrão RESTful e utiliza o formato JSON para a troca de dados. A autenticação é feita via JWT."));
    }
}
