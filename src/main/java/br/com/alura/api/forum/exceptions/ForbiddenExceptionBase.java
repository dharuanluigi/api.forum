package br.com.alura.api.forum.exceptions;

public abstract class ForbiddenExceptionBase extends RuntimeException {

    public ForbiddenExceptionBase(String message) {
        super(message);
    }
}
