package br.com.caiolobo.blogapplication.exceptions;

public class TokenPasswordMalformattedException extends RuntimeException{
    public TokenPasswordMalformattedException() {
        super("Token inválido.");
    }
}
