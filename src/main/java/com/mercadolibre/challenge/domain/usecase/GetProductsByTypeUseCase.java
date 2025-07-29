package com.mercadolibre.challenge.domain.usecase;

import com.mercadolibre.challenge.domain.model.Page;
import com.mercadolibre.challenge.domain.model.PageRequest;
import com.mercadolibre.challenge.domain.model.Product;
import com.mercadolibre.challenge.domain.port.input.GetProductsByTypeUseCasePort;
import com.mercadolibre.challenge.domain.port.output.ProductPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Implementation of the GetProductsByTypeUseCasePort
 * This use case retrieves products filtered by type
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GetProductsByTypeUseCase implements GetProductsByTypeUseCasePort {

    private final ProductPort productPort;

    /**
     * Get products by type
     * @param type the product type to filter by
     * @return a CompletableFuture containing a list of products of the specified type
     */
    @Override
    public CompletableFuture<List<Product>> execute(String type) {
        log.info("Getting products by type: {}", type);
        
        // Use the optimized method from the port instead of loading all products and filtering
        return productPort.findByType(type);
    }
    
    /**
     * Get products by type with pagination
     * @param type the product type to filter by
     * @param pageRequest the pagination information
     * @return a CompletableFuture containing a page of products of the specified type
     */
    @Override
    public CompletableFuture<Page<Product>> execute(String type, PageRequest pageRequest) {
        log.info("Getting products by type: {} with pagination: page={}, size={}", 
                type, pageRequest.getPage(), pageRequest.getSize());
        
        // Use the optimized method from the port that supports pagination
        return productPort.findByType(type, pageRequest);
    }
}