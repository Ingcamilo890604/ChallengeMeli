package com.mercadolibre.challenge.domain.exception;

/**
 * Exception thrown when a concurrency issue occurs
 * This could be due to race conditions, deadlocks, or other concurrency problems
 */
public class ConcurrencyException extends RuntimeException {
    
    public ConcurrencyException(String message) {
        super(message);
    }
    
    public ConcurrencyException(String message, Throwable cause) {
        super(message, cause);
    }
}