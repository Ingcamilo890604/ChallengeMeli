package com.mercadolibre.challenge.infrastructure.controller;

import com.mercadolibre.challenge.domain.model.Product;
import com.mercadolibre.challenge.domain.port.input.GetAllProductsUseCasePort;
import com.mercadolibre.challenge.domain.port.input.GetProductByIdUseCasePort;
import com.mercadolibre.challenge.domain.port.input.GetProductsByTypeUseCasePort;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private GetProductByIdUseCasePort getProductByIdUseCasePort;

    @Mock
    private GetAllProductsUseCasePort getAllProductsUseCasePort;

    @Mock
    private GetProductsByTypeUseCasePort getProductsByTypeUseCasePort;

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
        testProduct = Product.builder()
                .id(productId)
                .title("Test Product")
                .description("Test Description")
                .price(new BigDecimal("99.99"))
                .type("electronics")
                .build();

        testProductResponseDTO = ProductResponseDTO.builder()
                .id(productId)
                .title("Test Product")
                .description("Test Description")
                .price(new BigDecimal("99.99"))
                .type("electronics")
                .build();

        testProducts = Arrays.asList(
                testProduct,
                Product.builder()
                        .id("prod-002")
                        .title("Test Product 2")
                        .description("Test Description 2")
                        .price(new BigDecimal("199.99"))
                        .type("clothing")
                        .build()
        );

        testProductResponseDTOs = Arrays.asList(
                testProductResponseDTO,
                ProductResponseDTO.builder()
                        .id("prod-002")
                        .title("Test Product 2")
                        .description("Test Description 2")
                        .price(new BigDecimal("199.99"))
                        .type("clothing")
                        .build()
        );
    }

    @Test
    void testGetAllProductsShouldReturnAllProducts() throws ExecutionException, InterruptedException {
        when(getAllProductsUseCasePort.execute()).thenReturn(CompletableFuture.completedFuture(testProducts));
        when(productMapper.toResponseDTOs(testProducts)).thenReturn(testProductResponseDTOs);

        CompletableFuture<ResponseEntity<List<ProductResponseDTO>>> result = productController.getAllProducts();
        ResponseEntity<List<ProductResponseDTO>> responseEntity = result.get();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(testProductResponseDTOs, responseEntity.getBody());
        verify(getAllProductsUseCasePort).execute();
        verify(productMapper).toResponseDTOs(testProducts);
    }

    @Test
    void testGetProductByIdWhenProductExistsShouldReturnProduct() throws ExecutionException, InterruptedException {
        when(getProductByIdUseCasePort.execute(productId)).thenReturn(CompletableFuture.completedFuture(Optional.of(testProduct)));
        when(productMapper.toResponseDTO(testProduct)).thenReturn(testProductResponseDTO);

        CompletableFuture<ResponseEntity<ProductResponseDTO>> result = productController.getProductById(productId);
        ResponseEntity<ProductResponseDTO> responseEntity = result.get();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(testProductResponseDTO, responseEntity.getBody());
        verify(getProductByIdUseCasePort).execute(productId);
        verify(productMapper).toResponseDTO(testProduct);
    }

    @Test
    void testGetProductByIdWhenProductDoesNotExistShouldReturnNotFound() throws ExecutionException, InterruptedException {
        String nonExistentId = "non-existent-id";
        when(getProductByIdUseCasePort.execute(nonExistentId)).thenReturn(CompletableFuture.completedFuture(Optional.empty()));

        CompletableFuture<ResponseEntity<ProductResponseDTO>> result = productController.getProductById(nonExistentId);
        ResponseEntity<ProductResponseDTO> responseEntity = result.get();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(getProductByIdUseCasePort).execute(nonExistentId);
    }
    
    @Test
    void testGetProductsByTypeShouldReturnProductsOfSpecifiedType() throws ExecutionException, InterruptedException {
        String type = "electronics";
        List<Product> electronicsProducts = List.of(testProduct);
        List<ProductResponseDTO> electronicsProductDTOs = List.of(testProductResponseDTO);
        
        when(getProductsByTypeUseCasePort.execute(type)).thenReturn(CompletableFuture.completedFuture(electronicsProducts));
        when(productMapper.toResponseDTOs(electronicsProducts)).thenReturn(electronicsProductDTOs);

        CompletableFuture<ResponseEntity<List<ProductResponseDTO>>> result = productController.getProductsByType(type);
        ResponseEntity<List<ProductResponseDTO>> responseEntity = result.get();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(electronicsProductDTOs, responseEntity.getBody());
        verify(getProductsByTypeUseCasePort).execute(type);
        verify(productMapper).toResponseDTOs(electronicsProducts);
    }
    
    @Test
    void testGetProductsByTypeWhenNoProductsOfSpecifiedTypeShouldReturnEmptyList() throws ExecutionException, InterruptedException {
        String type = "books";
        List<Product> emptyList = Collections.emptyList();
        List<ProductResponseDTO> emptyDTOList = Collections.emptyList();
        
        when(getProductsByTypeUseCasePort.execute(type)).thenReturn(CompletableFuture.completedFuture(emptyList));
        when(productMapper.toResponseDTOs(emptyList)).thenReturn(emptyDTOList);

        CompletableFuture<ResponseEntity<List<ProductResponseDTO>>> result = productController.getProductsByType(type);
        ResponseEntity<List<ProductResponseDTO>> responseEntity = result.get();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
        verify(getProductsByTypeUseCasePort).execute(type);
        verify(productMapper).toResponseDTOs(emptyList);
    }
}