package com.mercadolibre.challenge.domain.usecase;

import com.mercadolibre.challenge.domain.model.Product;
import com.mercadolibre.challenge.domain.port.input.GetProductByIdUseCasePort;
import com.mercadolibre.challenge.domain.port.output.ProductPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Implementation of the GetProductByIdUseCase
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class GetProductByIdUseCase implements GetProductByIdUseCasePort {

    private final ProductPort productPort;

    @Override
    public CompletableFuture<Optional<Product>> execute(String id) {
        log.info("Getting product with id: {}", id);
        return productPort.findById(id);
    }
}