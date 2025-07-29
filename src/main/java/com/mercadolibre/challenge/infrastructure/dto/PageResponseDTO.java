package com.mercadolibre.challenge.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * DTO for pagination response
 * @param <T> the type of elements in the page
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDTO<T> {
    /**
     * The content of the page
     */
    @Builder.Default
    private List<T> content = Collections.emptyList();
    
    /**
     * The total number of elements across all pages
     */
    private long totalElements;
    
    /**
     * The total number of pages
     */
    private int totalPages;
    
    /**
     * The current page number (0-based)
     */
    private int page;
    
    /**
     * The page size
     */
    private int size;
    
    /**
     * Whether there is a previous page
     */
    private boolean hasPrevious;
    
    /**
     * Whether there is a next page
     */
    private boolean hasNext;
}