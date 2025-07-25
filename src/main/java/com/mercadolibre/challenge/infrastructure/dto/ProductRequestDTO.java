package com.mercadolibre.challenge.infrastructure.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for product creation and update requests
 */
@Builder
@Jacksonized
public record ProductRequestDTO(
    @NotBlank(message = "Title is required")
    String title,
    
    String description,
    
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than zero")
    BigDecimal price,
    
    List<String> images,
    
    @Valid
    List<PaymentMethodDTO> paymentMethods,
    
    @Valid
    SellerDTO seller,
    
    Integer stock
) {}