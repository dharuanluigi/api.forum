package br.com.alura.api.forum.middleware;

import br.com.alura.api.forum.dto.InsertDataErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ErrorValidationHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<InsertDataErrorDTO> handleErrorValidation(MethodArgumentNotValidException exception) {
        var errorFields = exception.getBindingResult().getFieldErrors();
        return errorFields.stream().map(f -> {
            var message = messageSource.getMessage(f, LocaleContextHolder.getLocale());
            return new InsertDataErrorDTO(f.getField(), message);
        }).toList();
    }
}
