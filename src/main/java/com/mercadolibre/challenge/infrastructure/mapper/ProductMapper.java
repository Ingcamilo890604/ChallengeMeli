package com.mercadolibre.challenge.infrastructure.mapper;

import com.mercadolibre.challenge.domain.model.Page;
import com.mercadolibre.challenge.domain.model.PaymentMethod;
import com.mercadolibre.challenge.domain.model.Product;
import com.mercadolibre.challenge.domain.model.Review;
import com.mercadolibre.challenge.domain.model.Seller;
import com.mercadolibre.challenge.infrastructure.dto.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Mapper class for converting between domain entities and DTOs
 */
@Component
public class ProductMapper {

    /**
     * Convert a ProductRequestDTO to a Product entity
     * @param requestDTO the request DTO
     * @return the Product entity
     */
    public Product toEntity(ProductRequestDTO requestDTO) {
        if (requestDTO == null) {
            return null;
        }

        return Product.builder()
                .title(requestDTO.title())
                .description(requestDTO.description())
                .price(requestDTO.price())
                .images(requestDTO.images())
                .paymentMethods(mapPaymentMethodsToEntities(requestDTO.paymentMethods()))
                .seller(mapSellerToEntity(requestDTO.seller()))
                .stock(requestDTO.stock())
                .rating(0.0) // Default rating for new products
                .reviews(new ArrayList<>()) // Empty reviews for new products
                .type(requestDTO.type())
                .build();
    }

    /**
     * Convert a Product entity to a ProductResponseDTO
     * @param product the Product entity
     * @return the response DTO
     */
    public ProductResponseDTO toResponseDTO(Product product) {
        if (product == null) {
            return null;
        }

        return ProductResponseDTO.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .images(product.getImages())
                .paymentMethods(mapPaymentMethodsToDTOs(product.getPaymentMethods()))
                .seller(mapSellerToDTO(product.getSeller()))
                .stock(product.getStock())
                .rating(product.getRating())
                .reviews(mapReviewsToDTOs(product.getReviews()))
                .type(product.getType())
                .build();
    }

    /**
     * Convert a list of Product entities to a list of ProductResponseDTOs
     * @param products the list of Product entities
     * @return the list of response DTOs
     */
    public List<ProductResponseDTO> toResponseDTOs(List<Product> products) {
        if (products == null) {
            return new ArrayList<>();
        }

        return products.stream()
                .map(this::toResponseDTO)
                .toList();
    }
    
    /**
     * Convert a Page of Product entities to a PageResponseDTO of ProductResponseDTOs
     * @param page the Page of Product entities
     * @return the PageResponseDTO of ProductResponseDTOs
     */
    public PageResponseDTO<ProductResponseDTO> toPageResponseDTO(Page<Product> page) {
        if (page == null) {
            return PageResponseDTO.<ProductResponseDTO>builder().build();
        }
        
        List<ProductResponseDTO> content = toResponseDTOs(page.getContent());
        
        return PageResponseDTO.<ProductResponseDTO>builder()
                .content(content)
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .page(page.getPage())
                .size(page.getSize())
                .hasPrevious(page.hasPrevious())
                .hasNext(page.hasNext())
                .build();
    }

    /**
     * Update a Product entity with data from a ProductRequestDTO
     * @param product the Product entity to update
     * @param requestDTO the request DTO with new data
     * @return the updated Product entity
     */
    public Product updateEntityFromDTO(Product product, ProductRequestDTO requestDTO) {
        if (product == null || requestDTO == null) {
            return product;
        }

        product.setTitle(requestDTO.title());
        product.setDescription(requestDTO.description());
        product.setPrice(requestDTO.price());
        product.setImages(requestDTO.images());
        product.setPaymentMethods(mapPaymentMethodsToEntities(requestDTO.paymentMethods()));
        product.setSeller(mapSellerToEntity(requestDTO.seller()));
        product.setStock(requestDTO.stock());
        product.setType(requestDTO.type());

        return product;
    }

    // Helper methods for mapping between entities and DTOs

    private List<PaymentMethod> mapPaymentMethodsToEntities(List<PaymentMethodDTO> dtos) {
        if (dtos == null) {
            return new ArrayList<>();
        }

        return dtos.stream()
                .map(this::mapPaymentMethodToEntity)
                .toList();
    }

    private PaymentMethod mapPaymentMethodToEntity(PaymentMethodDTO dto) {
        if (dto == null) {
            return null;
        }

        return PaymentMethod.builder()
                .id(dto.id() != null ? dto.id() : UUID.randomUUID().toString())
                .name(dto.name())
                .description(dto.description())
                .build();
    }

    private Seller mapSellerToEntity(SellerDTO dto) {
        if (dto == null) {
            return null;
        }

        return Seller.builder()
                .id(dto.id() != null ? dto.id() : UUID.randomUUID().toString())
                .name(dto.name())
                .email(dto.email())
                .phone(dto.phone())
                .rating(dto.rating())
                .build();
    }

    private List<PaymentMethodDTO> mapPaymentMethodsToDTOs(List<PaymentMethod> entities) {
        if (entities == null) {
            return new ArrayList<>();
        }

        return entities.stream()
                .map(this::mapPaymentMethodToDTO)
                .toList();
    }

    private PaymentMethodDTO mapPaymentMethodToDTO(PaymentMethod entity) {
        if (entity == null) {
            return null;
        }

        return PaymentMethodDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    private SellerDTO mapSellerToDTO(Seller entity) {
        if (entity == null) {
            return null;
        }

        return SellerDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .rating(entity.getRating())
                .build();
    }

    private List<ReviewDTO> mapReviewsToDTOs(List<Review> entities) {
        if (entities == null) {
            return new ArrayList<>();
        }

        return entities.stream()
                .map(this::mapReviewToDTO)
                .toList();
    }

    private ReviewDTO mapReviewToDTO(Review entity) {
        if (entity == null) {
            return null;
        }

        return ReviewDTO.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .userName(entity.getUserName())
                .comment(entity.getComment())
                .rating(entity.getRating())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}