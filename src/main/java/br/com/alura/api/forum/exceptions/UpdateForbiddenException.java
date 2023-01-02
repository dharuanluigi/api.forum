package br.com.alura.api.forum.exceptions;

public class UpdateForbiddenException extends ForbiddenExceptionBase {

    public UpdateForbiddenException(String message) {
        super(message);
    }
}
