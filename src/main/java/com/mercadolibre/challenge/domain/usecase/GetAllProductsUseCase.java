package com.mercadolibre.challenge.domain.usecase;

import com.mercadolibre.challenge.domain.model.Page;
import com.mercadolibre.challenge.domain.model.PageRequest;
import com.mercadolibre.challenge.domain.model.Product;
import com.mercadolibre.challenge.domain.port.input.GetAllProductsUseCasePort;
import com.mercadolibre.challenge.domain.port.output.ProductPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Implementation of the GetAllProductsUseCase
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class GetAllProductsUseCase implements GetAllProductsUseCasePort {

    private final ProductPort productPort;

    @Override
    public CompletableFuture<List<Product>> execute() {
        log.info("Getting all products");
        return productPort.findAll();
    }
    
    /**
     * Get all products with pagination
     * @param pageRequest the pagination information
     * @return a CompletableFuture containing a page of products
     */
    @Override
    public CompletableFuture<Page<Product>> execute(PageRequest pageRequest) {
        log.info("Getting all products with pagination: page={}, size={}", 
                pageRequest.getPage(), pageRequest.getSize());
        return productPort.findAll(pageRequest);
    }
}