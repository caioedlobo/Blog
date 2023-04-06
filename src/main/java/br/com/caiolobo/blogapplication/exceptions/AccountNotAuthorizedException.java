package br.com.caiolobo.blogapplication.exceptions;

public class AccountNotAuthorizedException extends RuntimeException{
    public AccountNotAuthorizedException() {
        super("Essa conta não existe.");
    }
}
