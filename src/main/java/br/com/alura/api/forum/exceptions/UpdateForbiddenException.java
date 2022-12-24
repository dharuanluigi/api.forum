package br.com.alura.api.forum.exceptions;

public class UpdateForbiddenException extends RuntimeException {

    public UpdateForbiddenException(String message) {
        super(message);
    }
}
