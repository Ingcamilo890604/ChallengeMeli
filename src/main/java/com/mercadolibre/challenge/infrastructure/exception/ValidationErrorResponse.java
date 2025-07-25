package com.mercadolibre.challenge.infrastructure.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Error response for validation errors
 * Extends the standard ErrorResponse to include validation error details
 */
@Getter
@Setter
public class ValidationErrorResponse extends ErrorResponse {
    
    private Map<String, String> errors;
    
    public ValidationErrorResponse(int status, String message, String path, LocalDateTime timestamp, Map<String, String> errors) {
        super(status, message, path, timestamp);
        this.errors = errors;
    }
}