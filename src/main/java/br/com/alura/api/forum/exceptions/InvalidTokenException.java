package br.com.alura.api.forum.exceptions;

public class InvalidTokenException extends ForbiddenExceptionBase {

    public InvalidTokenException(String message) {
        super(message);
    }
}
