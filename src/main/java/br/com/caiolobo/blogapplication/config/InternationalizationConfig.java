package br.com.caiolobo.blogapplication.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Locale;

@Configuration
public class InternationalizationConfig {

    @Bean
    public MessageSource messageSource(){       //fonte de mensagem é o message.properties
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");        //nao precisa colocar o .properties do messages.properties
        messageSource.setDefaultEncoding("ISO-8859-1");       //em qual padrão de Enconding está as mensagens (UTF-8)
        messageSource.setDefaultLocale(Locale.getDefault());    //pega do Brasil
        return messageSource;
    }
    //responsavel por fazer a intercalação do messageSource e vai trocar o codigo pela mensagem
    public LocalValidatorFactoryBean validatorFactoryBean(){
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
}

