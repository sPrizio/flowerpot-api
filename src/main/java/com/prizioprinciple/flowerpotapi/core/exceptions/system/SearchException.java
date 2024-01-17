package com.prizioprinciple.flowerpotapi.core.exceptions.system;

/**
 * Exception thrown when search fails
 *
 * @author Stephen Prizio
 * @version 0.0.1
 */
public class SearchException extends RuntimeException {

    public SearchException(final String message) {
        super(message);
    }

    public SearchException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
