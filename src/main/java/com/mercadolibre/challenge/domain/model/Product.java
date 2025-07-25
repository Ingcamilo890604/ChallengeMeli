package com.mercadolibre.challenge.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String id;
    private String title;
    private String description;
    private BigDecimal price;
    private List<String> images;
    private List<PaymentMethod> paymentMethods;
    private Seller seller;
    private Integer stock;
    private Double rating;
    private List<Review> reviews;
}