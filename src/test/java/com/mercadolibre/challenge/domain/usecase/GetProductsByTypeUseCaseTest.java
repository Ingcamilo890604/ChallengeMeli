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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetProductsByTypeUseCaseTest {

    @Mock
    private ProductPort productPort;

    @InjectMocks
    private GetProductsByTypeUseCase getProductsByTypeUseCase;

    private Product smartphone;
    private Product laptop;
    private Product headphones;

    @BeforeEach
    void setUp() {
        smartphone = Product.builder()
                .id("prod-001")
                .title("Smartphone Samsung Galaxy S21")
                .description("Último modelo de Samsung con cámara de alta resolución y pantalla AMOLED")
                .price(new BigDecimal("799.99"))
                .type("smartphone")
                .build();

        laptop = Product.builder()
                .id("prod-002")
                .title("Laptop Dell XPS 15")
                .description("Potente laptop con procesador Intel i7, 16GB RAM y SSD de 512GB")
                .price(new BigDecimal("1299.99"))
                .type("laptop")
                .build();

        headphones = Product.builder()
                .id("prod-003")
                .title("Auriculares Sony WH-1000XM4")
                .description("Auriculares inalámbricos con cancelación de ruido")
                .price(new BigDecimal("349.99"))
                .type("headphones")
                .build();
    }

    @Test
    void shouldReturnProductsOfSpecifiedType() throws ExecutionException, InterruptedException {
        // Given
        List<Product> allProducts = Arrays.asList(smartphone, laptop, headphones);
        when(productPort.findAll()).thenReturn(CompletableFuture.completedFuture(allProducts));

        // When
        List<Product> result = getProductsByTypeUseCase.execute("smartphone").get();

        // Then
        assertEquals(1, result.size());
        assertEquals("smartphone", result.get(0).getType());
        assertEquals("prod-001", result.get(0).getId());
    }

    @Test
    void shouldReturnEmptyListWhenNoProductsOfSpecifiedType() throws ExecutionException, InterruptedException {
        // Given
        List<Product> allProducts = Arrays.asList(smartphone, laptop, headphones);
        when(productPort.findAll()).thenReturn(CompletableFuture.completedFuture(allProducts));

        // When
        List<Product> result = getProductsByTypeUseCase.execute("tv").get();

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnEmptyListWhenNoProducts() throws ExecutionException, InterruptedException {
        // Given
        when(productPort.findAll()).thenReturn(CompletableFuture.completedFuture(Collections.emptyList()));

        // When
        List<Product> result = getProductsByTypeUseCase.execute("smartphone").get();

        // Then
        assertTrue(result.isEmpty());
    }
}