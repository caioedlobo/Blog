package br.com.caiolobo.blogapplication.exceptions;

public class AccountAlreadyExistsException extends RuntimeException{
    public AccountAlreadyExistsException() {
        super("Usuário já existe");
    }
}
