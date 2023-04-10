package br.com.caiolobo.blogapplication.exceptions;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException() {
        super("Conta não encontrada.");
    }
}
