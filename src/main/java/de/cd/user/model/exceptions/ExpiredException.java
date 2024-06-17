package de.cd.user.model.exceptions;

/**
 * Expired Exception class. Will be thrown if something has expired
 */
public class ExpiredException extends RuntimeException {
    public ExpiredException(String message) {
        super(message);
    }
}
