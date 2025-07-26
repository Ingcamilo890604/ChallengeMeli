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
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetProductByIdUseCaseTest {

    @Mock
    private ProductPort productPort;

    @InjectMocks
    private GetProductByIdUseCase getProductByIdUseCase;

    private Product testProduct;
    private final String productId = "prod-001";

    @BeforeEach
    void setUp() {
        testProduct = Product.builder()
                .id(productId)
                .title("Test Product")
                .description("Test Description")
                .price(new BigDecimal("99.99"))
                .build();
    }

    @Test
    void execute_whenProductExists_shouldReturnProduct() throws ExecutionException, InterruptedException {
        // Arrange
        when(productPort.findById(productId)).thenReturn(CompletableFuture.completedFuture(Optional.of(testProduct)));

        // Act
        CompletableFuture<Optional<Product>> result = getProductByIdUseCase.execute(productId);
        Optional<Product> productOptional = result.get();

        // Assert
        assertTrue(productOptional.isPresent());
        assertEquals(testProduct, productOptional.get());
        verify(productPort).findById(productId);
    }

    @Test
    void execute_whenProductDoesNotExist_shouldReturnEmptyOptional() throws ExecutionException, InterruptedException {
        // Arrange
        String nonExistentId = "non-existent-id";
        when(productPort.findById(nonExistentId)).thenReturn(CompletableFuture.completedFuture(Optional.empty()));

        // Act
        CompletableFuture<Optional<Product>> result = getProductByIdUseCase.execute(nonExistentId);
        Optional<Product> productOptional = result.get();

        // Assert
        assertFalse(productOptional.isPresent());
        verify(productPort).findById(nonExistentId);
    }
}