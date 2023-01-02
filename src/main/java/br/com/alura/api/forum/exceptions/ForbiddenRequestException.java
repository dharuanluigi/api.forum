package br.com.alura.api.forum.exceptions;

public class ForbiddenRequestException extends ForbiddenExceptionBase {
    public ForbiddenRequestException(String message) {
        super(message);
    }
}
