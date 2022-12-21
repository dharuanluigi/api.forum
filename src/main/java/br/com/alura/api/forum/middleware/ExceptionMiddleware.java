package br.com.alura.api.forum.middleware;

import br.com.alura.api.forum.dto.ErrorResponseDTO;
import br.com.alura.api.forum.dto.InsertDataErrorDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestControllerAdvice
public class ExceptionMiddleware {

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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> notFoundHandler(EntityNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ErrorResponseDTO constraintException(SQLIntegrityConstraintViolationException e) {
        return new ErrorResponseDTO("Error when manipulate your request, contact system admin");
    }
}
