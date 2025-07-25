package com.mercadolibre.challenge.infrastructure.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

/**
 * DTO for seller data in requests and responses
 */
@Builder
@Jacksonized
public record SellerDTO(
    String id,
    
    @NotBlank(message = "Seller name is required")
    String name,
    
    @Email(message = "Invalid email format")
    String email,
    
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be between 10 and 15 digits")
    String phone,
    
    Double rating
) {}