package com.mercadolibre.challenge.infrastructure.adapter.output;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.challenge.domain.exception.ConcurrencyException;
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
public class FileProductAdapter implements ProductPort {

    private final ObjectMapper objectMapper;
    private final String dataFilePath;
    private final ConcurrentHashMap<String, Product> productCache = new ConcurrentHashMap<>();
    private final ReadWriteLock fileLock = new ReentrantReadWriteLock();

    public FileProductAdapter(ObjectMapper objectMapper, 
                            @Value("${app.data.file-path:data/products.json}") String dataFilePath) {
        this.objectMapper = objectMapper;
        this.dataFilePath = dataFilePath;
    }

    @PostConstruct
    public void init() {
        try {
            // Create directory if it doesn't exist
            Path dataDirectory = Paths.get(dataFilePath).getParent();
            if (dataDirectory != null) {
                Files.createDirectories(dataDirectory);
            }
            loadDataFromFile();
        } catch (IOException e) {
            log.error("Error initializing file adapter", e);
            throw new RuntimeException("Could not initialize file adapter", e);
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
            throw new ConcurrencyException("Could not load data from file", e);
        } finally {
            fileLock.readLock().unlock();
        }
    }
}