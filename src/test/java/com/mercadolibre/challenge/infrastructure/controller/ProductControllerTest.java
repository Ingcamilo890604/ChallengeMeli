package com.mercadolibre.challenge.infrastructure.controller;

import com.mercadolibre.challenge.domain.model.Product;
import com.mercadolibre.challenge.domain.port.input.GetAllProductsUseCasePort;
import com.mercadolibre.challenge.domain.port.input.GetProductByIdUseCasePort;
import com.mercadolibre.challenge.infrastructure.dto.ProductResponseDTO;
import com.mercadolibre.challenge.infrastructure.mapper.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private GetProductByIdUseCasePort getProductByIdUseCasePort;

    @Mock
    private GetAllProductsUseCasePort getAllProductsUseCasePort;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductController productController;

    private Product testProduct;
    private ProductResponseDTO testProductResponseDTO;
    private List<Product> testProducts;
    private List<ProductResponseDTO> testProductResponseDTOs;
    private final String productId = "prod-001";

    @BeforeEach
    void setUp() {
        // Set up test product
        testProduct = Product.builder()
                .id(productId)
                .title("Test Product")
                .description("Test Description")
                .price(new BigDecimal("99.99"))
                .build();

        // Set up test product response DTO
        testProductResponseDTO = ProductResponseDTO.builder()
                .id(productId)
                .title("Test Product")
                .description("Test Description")
                .price(new BigDecimal("99.99"))
                .build();

        // Set up test products list
        testProducts = Arrays.asList(
                testProduct,
                Product.builder()
                        .id("prod-002")
                        .title("Test Product 2")
                        .description("Test Description 2")
                        .price(new BigDecimal("199.99"))
                        .build()
        );

        // Set up test product response DTOs list
        testProductResponseDTOs = Arrays.asList(
                testProductResponseDTO,
                ProductResponseDTO.builder()
                        .id("prod-002")
                        .title("Test Product 2")
                        .description("Test Description 2")
                        .price(new BigDecimal("199.99"))
                        .build()
        );
    }

    @Test
    void getAllProducts_shouldReturnAllProducts() throws ExecutionException, InterruptedException {
        // Arrange
        when(getAllProductsUseCasePort.execute()).thenReturn(CompletableFuture.completedFuture(testProducts));
        when(productMapper.toResponseDTOs(testProducts)).thenReturn(testProductResponseDTOs);

        // Act
        CompletableFuture<ResponseEntity<List<ProductResponseDTO>>> result = productController.getAllProducts();
        ResponseEntity<List<ProductResponseDTO>> responseEntity = result.get();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(testProductResponseDTOs, responseEntity.getBody());
        verify(getAllProductsUseCasePort).execute();
        verify(productMapper).toResponseDTOs(testProducts);
    }

    @Test
    void getProductById_whenProductExists_shouldReturnProduct() throws ExecutionException, InterruptedException {
        // Arrange
        when(getProductByIdUseCasePort.execute(productId)).thenReturn(CompletableFuture.completedFuture(Optional.of(testProduct)));
        when(productMapper.toResponseDTO(testProduct)).thenReturn(testProductResponseDTO);

        // Act
        CompletableFuture<ResponseEntity<ProductResponseDTO>> result = productController.getProductById(productId);
        ResponseEntity<ProductResponseDTO> responseEntity = result.get();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(testProductResponseDTO, responseEntity.getBody());
        verify(getProductByIdUseCasePort).execute(productId);
        verify(productMapper).toResponseDTO(testProduct);
    }

    @Test
    void getProductById_whenProductDoesNotExist_shouldReturnNotFound() throws ExecutionException, InterruptedException {
        // Arrange
        String nonExistentId = "non-existent-id";
        when(getProductByIdUseCasePort.execute(nonExistentId)).thenReturn(CompletableFuture.completedFuture(Optional.empty()));

        // Act
        CompletableFuture<ResponseEntity<ProductResponseDTO>> result = productController.getProductById(nonExistentId);
        ResponseEntity<ProductResponseDTO> responseEntity = result.get();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(getProductByIdUseCasePort).execute(nonExistentId);
    }
}