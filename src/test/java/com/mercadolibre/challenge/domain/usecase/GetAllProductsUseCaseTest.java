package com.mercadolibre.challenge.domain.usecase;

import com.mercadolibre.challenge.domain.model.Product;
import com.mercadolibre.challenge.domain.port.output.ProductPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllProductsUseCaseTest {

    @Mock
    private ProductPort productPort;

    @InjectMocks
    private GetAllProductsUseCase getAllProductsUseCase;

    private List<Product> testProducts;

    @BeforeEach
    void setUp() {
        testProducts = Arrays.asList(
                Product.builder()
                        .id("prod-001")
                        .title("Test Product 1")
                        .description("Test Description 1")
                        .price(new BigDecimal("99.99"))
                        .build(),
                Product.builder()
                        .id("prod-002")
                        .title("Test Product 2")
                        .description("Test Description 2")
                        .price(new BigDecimal("199.99"))
                        .build()
        );
    }

    @Test
    void execute_whenProductsExist_shouldReturnAllProducts() throws ExecutionException, InterruptedException {
        // Arrange
        when(productPort.findAll()).thenReturn(CompletableFuture.completedFuture(testProducts));

        // Act
        CompletableFuture<List<Product>> result = getAllProductsUseCase.execute();
        List<Product> products = result.get();

        // Assert
        assertEquals(2, products.size());
        assertEquals(testProducts, products);
        verify(productPort).findAll();
    }

    @Test
    void execute_whenNoProductsExist_shouldReturnEmptyList() throws ExecutionException, InterruptedException {
        // Arrange
        when(productPort.findAll()).thenReturn(CompletableFuture.completedFuture(new ArrayList<>()));

        // Act
        CompletableFuture<List<Product>> result = getAllProductsUseCase.execute();
        List<Product> products = result.get();

        // Assert
        assertTrue(products.isEmpty());
        verify(productPort).findAll();
    }
}