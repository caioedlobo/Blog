package br.com.caiolobo.blogapplication.exceptions;

public class TokenPasswordVerificationException extends RuntimeException{
    public TokenPasswordVerificationException() {
        super("Erro na verificação do token. Por favor, gere outro token.");
    }
}
