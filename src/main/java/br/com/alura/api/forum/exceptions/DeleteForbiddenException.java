package br.com.alura.api.forum.exceptions;

public class DeleteForbiddenException extends ForbiddenExceptionBase {

    public DeleteForbiddenException(String message) {
        super(message);
    }
}
