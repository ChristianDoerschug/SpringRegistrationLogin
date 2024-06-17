package de.cd.user.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * PasswordNotValidException class. Throws Exception when the password is not valid
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PasswordNotValidException extends RuntimeException {
    public PasswordNotValidException(String message) {
        super(message);
    }
}
