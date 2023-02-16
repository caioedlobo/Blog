package br.com.caiolobo.blogapplication;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class ApiErrrors {

    @Getter
    private List<String> errors;

    public ApiErrrors(List<String> errors) {
        this.errors = errors;
    }

    public ApiErrrors(String message) {
        this.errors = Arrays.asList(message);

    }
}
