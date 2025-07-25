package com.mercadolibre.challenge.domain.port.input;

import com.mercadolibre.challenge.domain.model.Product;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Input port for retrieving all products
 * Following hexagonal architecture principles, this interface is defined in the domain layer
 * as an input port and will be used by adapters in the infrastructure layer
 */
public interface GetAllProductsUseCasePort {
    
    /**
     * Get all products
     * @return a CompletableFuture containing a list of all products
     */
    CompletableFuture<List<Product>> execute();
}