package com.mercadolibre.challenge.infrastructure.dto;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for product responses
 */
@Builder
@Jacksonized
public record ProductResponseDTO(
    String id,
    String title,
    String description,
    BigDecimal price,
    List<String> images,
    List<PaymentMethodDTO> paymentMethods,
    SellerDTO seller,
    Integer stock,
    Double rating,
    List<ReviewDTO> reviews,
    String type
) {}