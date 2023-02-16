package br.com.caiolobo.blogapplication.exceptions;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException() {
        super("Post não encontrado.");
    }
}
