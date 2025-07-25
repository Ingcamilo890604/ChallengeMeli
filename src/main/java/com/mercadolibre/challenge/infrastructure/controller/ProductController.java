package com.mercadolibre.challenge.infrastructure.controller;

import com.mercadolibre.challenge.domain.port.input.GetAllProductsUseCasePort;
import com.mercadolibre.challenge.domain.port.input.GetProductByIdUseCasePort;
import com.mercadolibre.challenge.infrastructure.dto.ProductResponseDTO;
import com.mercadolibre.challenge.infrastructure.mapper.ProductMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * REST controller for product endpoints
 * Only provides functionality to get all products and get product by ID
 */
@Tag(name = "Product", description = "Product management API")
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final GetProductByIdUseCasePort getProductByIdUseCasePort;
    private final GetAllProductsUseCasePort getAllProductsUseCase;
    private final ProductMapper productMapper;

    /**
     * Get all products
     * @return a list of all products
     */
    @Operation(summary = "Get all products", description = "Returns a list of all available products")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of products",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ProductResponseDTO.class)))
    })
    @GetMapping
    public CompletableFuture<ResponseEntity<List<ProductResponseDTO>>> getAllProducts() {
        log.info("REST request to get all products");
        return getAllProductsUseCase.execute()
                .thenApply(products -> ResponseEntity.ok(productMapper.toResponseDTOs(products)));
    }

    /**
     * Get a product by its ID
     * @param id the product ID
     * @return the product if found
     */
    @Operation(summary = "Get a product by ID", description = "Returns a product based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved product",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ProductResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Product not found",
                content = @Content)
    })
    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<ProductResponseDTO>> getProductById(
            @Parameter(description = "ID of the product to retrieve", required = true)
            @PathVariable String id) {
        log.info("REST request to get product with id: {}", id);
        return getProductByIdUseCasePort.execute(id)
                .thenApply(optionalProduct -> optionalProduct
                        .map(product -> ResponseEntity.ok(productMapper.toResponseDTO(product)))
                        .orElse(ResponseEntity.notFound().build()));
    }
}