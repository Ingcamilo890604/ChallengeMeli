package com.mercadolibre.challenge.infrastructure.controller;

import com.mercadolibre.challenge.domain.model.PageRequest;
import com.mercadolibre.challenge.domain.port.input.GetAllProductsUseCasePort;
import com.mercadolibre.challenge.domain.port.input.GetProductByIdUseCasePort;
import com.mercadolibre.challenge.domain.port.input.GetProductsByTypeUseCasePort;
import com.mercadolibre.challenge.infrastructure.dto.PageResponseDTO;
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
    private final GetProductsByTypeUseCasePort getProductsByTypeUseCasePort;
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
     * Get all products with pagination
     * @param page the page number (0-based)
     * @param size the page size
     * @return a page of products
     */
    @Operation(summary = "Get all products with pagination", description = "Returns a page of products")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved page of products",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = PageResponseDTO.class)))
    })
    @GetMapping("/page")
    public CompletableFuture<ResponseEntity<PageResponseDTO<ProductResponseDTO>>> getAllProductsPaginated(
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        log.info("REST request to get all products with pagination: page={}, size={}", page, size);
        PageRequest pageRequest = PageRequest.of(page, size);
        return getAllProductsUseCase.execute(pageRequest)
                .thenApply(productPage -> ResponseEntity.ok(productMapper.toPageResponseDTO(productPage)));
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
    
    /**
     * Get products by type
     * @param type the product type to filter by
     * @return a list of products of the specified type
     */
    @Operation(summary = "Get products by type", description = "Returns a list of products filtered by type")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of products",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ProductResponseDTO.class)))
    })
    @GetMapping("/type/{type}")
    public CompletableFuture<ResponseEntity<List<ProductResponseDTO>>> getProductsByType(
            @Parameter(description = "Type of products to retrieve", required = true)
            @PathVariable String type) {
        log.info("REST request to get products with type: {}", type);
        return getProductsByTypeUseCasePort.execute(type)
                .thenApply(products -> ResponseEntity.ok(productMapper.toResponseDTOs(products)));
    }
    
    /**
     * Get products by type with pagination
     * @param type the product type to filter by
     * @param page the page number (0-based)
     * @param size the page size
     * @return a page of products of the specified type
     */
    @Operation(summary = "Get products by type with pagination", description = "Returns a page of products filtered by type")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved page of products",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = PageResponseDTO.class)))
    })
    @GetMapping("/type/{type}/page")
    public CompletableFuture<ResponseEntity<PageResponseDTO<ProductResponseDTO>>> getProductsByTypePaginated(
            @Parameter(description = "Type of products to retrieve", required = true)
            @PathVariable String type,
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        log.info("REST request to get products with type: {} and pagination: page={}, size={}", 
                type, page, size);
        PageRequest pageRequest = PageRequest.of(page, size);
        return getProductsByTypeUseCasePort.execute(type, pageRequest)
                .thenApply(productPage -> ResponseEntity.ok(productMapper.toPageResponseDTO(productPage)));
    }
}