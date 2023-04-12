package br.com.caiolobo.blogapplication.exceptions;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException() {
        super("Token expirado.");
    }
}
