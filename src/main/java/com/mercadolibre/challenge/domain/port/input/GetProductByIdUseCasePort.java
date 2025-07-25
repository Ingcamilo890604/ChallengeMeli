package com.mercadolibre.challenge.domain.port.input;

import com.mercadolibre.challenge.domain.model.Product;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Input port for retrieving a product by its ID
 * Following hexagonal architecture principles, this interface is defined in the domain layer
 * as an input port and will be used by adapters in the infrastructure layer
 */
public interface GetProductByIdUseCasePort {
    
    /**
     * Get a product by its ID
     * @param id the product ID
     * @return a CompletableFuture containing an Optional with the product if found
     */
    CompletableFuture<Optional<Product>> execute(String id);
}