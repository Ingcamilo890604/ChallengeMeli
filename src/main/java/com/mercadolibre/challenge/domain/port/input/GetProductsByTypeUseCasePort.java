package com.mercadolibre.challenge.domain.port.input;

import com.mercadolibre.challenge.domain.model.Page;
import com.mercadolibre.challenge.domain.model.PageRequest;
import com.mercadolibre.challenge.domain.model.Product;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Input port for getting products by type
 * Following hexagonal architecture principles, this interface is defined in the domain layer
 * as an input port and will be implemented by a use case in the domain layer
 */
public interface GetProductsByTypeUseCasePort {
    
    /**
     * Get products by type
     * @param type the product type to filter by
     * @return a CompletableFuture containing a list of products of the specified type
     */
    CompletableFuture<List<Product>> execute(String type);
    
    /**
     * Get products by type with pagination
     * @param type the product type to filter by
     * @param pageRequest the pagination information
     * @return a CompletableFuture containing a page of products of the specified type
     */
    CompletableFuture<Page<Product>> execute(String type, PageRequest pageRequest);
}