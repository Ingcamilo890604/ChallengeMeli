package com.mercadolibre.challenge.domain.port.output;

import com.mercadolibre.challenge.domain.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Output port for Product entity operations
 * Following hexagonal architecture principles, this interface is defined in the domain layer
 * as an output port and will be implemented by an adapter in the infrastructure layer
 */
public interface ProductPort {
    
    /**
     * Find a product by its ID
     * @param id the product ID
     * @return a CompletableFuture containing an Optional with the product if found
     */
    CompletableFuture<Optional<Product>> findById(String id);
    
    /**
     * Find all products
     * @return a CompletableFuture containing a list of all products
     */
    CompletableFuture<List<Product>> findAll();


}