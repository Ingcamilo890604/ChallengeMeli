package com.mercadolibre.challenge.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * Represents a page of data
 * Contains the content and metadata about the page
 * @param <T> the type of elements in the page
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Page<T> {
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
     * @return true if there is a previous page
     */
    public boolean hasPrevious() {
        return page > 0;
    }
    
    /**
     * Whether there is a next page
     * @return true if there is a next page
     */
    public boolean hasNext() {
        return page < totalPages - 1;
    }
    
    /**
     * Creates an empty page
     * @param <T> the type of elements in the page
     * @return an empty page
     */
    public static <T> Page<T> empty() {
        return Page.<T>builder().build();
    }
    
    /**
     * Creates a page with the given content and page information
     * @param content the content of the page
     * @param totalElements the total number of elements across all pages
     * @param pageRequest the page request
     * @param <T> the type of elements in the page
     * @return a new page
     */
    public static <T> Page<T> of(List<T> content, long totalElements, PageRequest pageRequest) {
        int totalPages = pageRequest.getSize() > 0 ? 
                (int) Math.ceil((double) totalElements / pageRequest.getSize()) : 0;
        
        return Page.<T>builder()
                .content(content)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .page(pageRequest.getPage())
                .size(pageRequest.getSize())
                .build();
    }
}