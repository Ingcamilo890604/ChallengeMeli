package com.mercadolibre.challenge.infrastructure.adapter.output;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.challenge.domain.exception.ConcurrencyException;
import com.mercadolibre.challenge.domain.exception.InitializationException;
import com.mercadolibre.challenge.domain.model.Page;
import com.mercadolibre.challenge.domain.model.PageRequest;
import com.mercadolibre.challenge.domain.model.Product;
import com.mercadolibre.challenge.domain.port.output.ProductPort;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Output adapter for product persistence
 * This adapter implements the ProductPort interface and persists products in a JSON file
 * following the hexagonal architecture pattern
 */
@Repository
@Slf4j
public class ProductAdapter implements ProductPort {

    private final ObjectMapper objectMapper;
    private final String dataFilePath;
    private final ConcurrentHashMap<String, Product> productCache = new ConcurrentHashMap<>();
    // Index for products by type to optimize search
    private final ConcurrentHashMap<String, List<String>> productsByTypeIndex = new ConcurrentHashMap<>();
    private final ReadWriteLock fileLock = new ReentrantReadWriteLock();

    public ProductAdapter(ObjectMapper objectMapper,
                          @Value("${app.data.file-path:data/products.json}") String dataFilePath) {
        this.objectMapper = objectMapper;
        this.dataFilePath = dataFilePath;
    }

    @PostConstruct
    public void init() {
        try {
            Path dataDirectory = Paths.get(dataFilePath).getParent();
            if (dataDirectory != null) {
                Files.createDirectories(dataDirectory);
            }
            loadDataFromFile();
            buildTypeIndex();
        } catch (IOException e) {
            log.error("Error initializing file adapter", e);
            throw new InitializationException("Could not initialize file adapter", e);
        }
    }

    @Override
    public CompletableFuture<Optional<Product>> findById(String id) {
        return CompletableFuture.supplyAsync(() -> Optional.ofNullable(productCache.get(id)));
    }

    @Override
    public CompletableFuture<List<Product>> findAll() {
        return CompletableFuture.supplyAsync(() -> new ArrayList<>(productCache.values()));
    }

    @Override
    public CompletableFuture<Page<Product>> findAll(PageRequest pageRequest) {
        return CompletableFuture.supplyAsync(() -> {
            List<Product> allProducts = new ArrayList<>(productCache.values());
            long totalElements = allProducts.size();
            
            // Apply pagination
            List<Product> pagedProducts = applyPagination(allProducts, pageRequest);
            
            return Page.of(pagedProducts, totalElements, pageRequest);
        });
    }

    @Override
    public CompletableFuture<List<Product>> findByType(String type) {
        return CompletableFuture.supplyAsync(() -> {
            // Use the type index to get product IDs of the specified type
            List<String> productIds = productsByTypeIndex.getOrDefault(type, new ArrayList<>());
            
            // Map product IDs to actual products
            return productIds.stream()
                    .map(productCache::get)
                    .toList();
        });
    }

    @Override
    public CompletableFuture<Page<Product>> findByType(String type, PageRequest pageRequest) {
        return CompletableFuture.supplyAsync(() -> {
            // Use the type index to get product IDs of the specified type
            List<String> productIds = productsByTypeIndex.getOrDefault(type, new ArrayList<>());
            long totalElements = productIds.size();
            
            // Map product IDs to actual products
            List<Product> productsOfType = productIds.stream()
                    .map(productCache::get)
                    .toList();
            
            // Apply pagination
            List<Product> pagedProducts = applyPagination(productsOfType, pageRequest);
            
            return Page.of(pagedProducts, totalElements, pageRequest);
        });
    }

    /**
     * Load data from file into cache
     * Uses a read lock to prevent concurrent writes
     */
    private void loadDataFromFile() {
        fileLock.readLock().lock();
        try {
            File file = new File(dataFilePath);
            if (file.length() > 0) {
                List<Product> products = objectMapper.readValue(file, new TypeReference<List<Product>>() {});
                productCache.clear();
                products.forEach(product -> productCache.put(product.getId(), product));
                log.info("Loaded {} products from file", products.size());
            }
        } catch (IOException e) {
            log.error("Error loading data from file", e);
            throw new InitializationException("Could not load data from file", e);
        } finally {
            fileLock.readLock().unlock();
        }
    }
    
    /**
     * Build an index of products by type for faster lookups
     */
    private void buildTypeIndex() {
        productsByTypeIndex.clear();
        
        for (Map.Entry<String, Product> entry : productCache.entrySet()) {
            String productId = entry.getKey();
            Product product = entry.getValue();
            String type = product.getType();
            
            if (type != null) {
                productsByTypeIndex.computeIfAbsent(type, k -> new ArrayList<>()).add(productId);
            }
        }
        
        log.info("Built type index with {} different types", productsByTypeIndex.size());
    }
    
    /**
     * Apply pagination to a list of products
     * @param products the list of products to paginate
     * @param pageRequest the pagination information
     * @return a paginated list of products
     */
    private List<Product> applyPagination(List<Product> products, PageRequest pageRequest) {
        int offset = pageRequest.getOffset();
        int limit = pageRequest.getSize();
        
        return products.stream()
                .skip(offset)
                .limit(limit)
                .toList();
    }
}