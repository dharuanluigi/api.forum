package br.com.alura.api.forum.middleware;

import br.com.alura.api.forum.dto.ErrorResponseDTO;
import br.com.alura.api.forum.dto.InsertDataErrorDTO;
import br.com.alura.api.forum.exceptions.ForbiddenExceptionBase;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.regex.Pattern;

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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponseDTO invalidArgumentException(IllegalArgumentException e) {
        return new ErrorResponseDTO(e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponseDTO notFoundHandler(EntityNotFoundException exception) {
        return new ErrorResponseDTO(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ErrorResponseDTO constraintException(SQLIntegrityConstraintViolationException e) {
        var emailRegex = Pattern.compile("@", Pattern.CASE_INSENSITIVE);
        var matcher = emailRegex.matcher(e.getMessage());

        if (matcher.find()) {
            return new ErrorResponseDTO("E-mail already was used, choose another one");
        }

        var usernameRegex = Pattern.compile("username", Pattern.CASE_INSENSITIVE);
        var matcherUsername = usernameRegex.matcher(e.getMessage());

        if (matcherUsername.find()) {
            return new ErrorResponseDTO("Username already was used, choose another one");
        }
        System.out.println(e.getMessage());
        return new ErrorResponseDTO("Invalid operation, contact system admin");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenExceptionBase.class)
    public ErrorResponseDTO forbiddenException(ForbiddenExceptionBase e) {
        return new ErrorResponseDTO(e.getMessage());
    }
}
