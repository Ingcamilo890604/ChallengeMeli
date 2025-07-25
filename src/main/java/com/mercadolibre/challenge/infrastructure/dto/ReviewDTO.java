package com.mercadolibre.challenge.infrastructure.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

/**
 * DTO for review data in responses
 */
@Builder
@Jacksonized
public record ReviewDTO(
    String id,
    
    String userId,
    
    @NotBlank(message = "User name is required")
    String userName,
    
    String comment,
    
    @Min(value = 1, message = "Rating must be between 1 and 5")
    @Max(value = 5, message = "Rating must be between 1 and 5")
    Integer rating,
    
    LocalDateTime createdAt
) {}