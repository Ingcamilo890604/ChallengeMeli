package com.mercadolibre.challenge.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request for a page of data
 * Contains information about the requested page number and size
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRequest {
    /**
     * The page number (0-based)
     */
    @Builder.Default
    private int page = 0;
    
    /**
     * The page size
     */
    @Builder.Default
    private int size = 10;
    
    /**
     * Creates a PageRequest with the given page number and size
     * @param page the page number (0-based)
     * @param size the page size
     * @return a new PageRequest
     */
    public static PageRequest of(int page, int size) {
        return PageRequest.builder()
                .page(Math.max(0, page))
                .size(Math.max(1, size))
                .build();
    }
    
    /**
     * Creates a default PageRequest with page 0 and size 10
     * @return a new PageRequest
     */
    public static PageRequest ofDefault() {
        return PageRequest.builder().build();
    }
    
    /**
     * Calculates the offset for the current page
     * @return the offset
     */
    public int getOffset() {
        return page * size;
    }
}