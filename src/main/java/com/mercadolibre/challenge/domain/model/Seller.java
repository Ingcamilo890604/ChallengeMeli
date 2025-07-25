package com.mercadolibre.challenge.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seller {
    private String id;
    private String name;
    private String email;
    private String phone;
    private Double rating;
}