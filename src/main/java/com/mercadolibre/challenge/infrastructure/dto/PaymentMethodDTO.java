package com.mercadolibre.challenge.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

/**
 * DTO for payment method data in requests and responses
 */
@Builder
@Jacksonized
public record PaymentMethodDTO(
    String id,
    
    @NotBlank(message = "Payment method name is required")
    String name,
    
    String description
) {}