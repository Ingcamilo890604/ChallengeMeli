package com.mercadolibre.challenge.infrastructure.mapper;

import com.mercadolibre.challenge.domain.model.PaymentMethod;
import com.mercadolibre.challenge.domain.model.Product;
import com.mercadolibre.challenge.domain.model.Review;
import com.mercadolibre.challenge.domain.model.Seller;
import com.mercadolibre.challenge.infrastructure.dto.PaymentMethodDTO;
import com.mercadolibre.challenge.infrastructure.dto.ProductRequestDTO;
import com.mercadolibre.challenge.infrastructure.dto.ProductResponseDTO;
import com.mercadolibre.challenge.infrastructure.dto.ReviewDTO;
import com.mercadolibre.challenge.infrastructure.dto.SellerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    private ProductMapper productMapper;
    private Product testProduct;
    private ProductRequestDTO testProductRequestDTO;
    private List<PaymentMethod> testPaymentMethods;
    private List<PaymentMethodDTO> testPaymentMethodDTOs;
    private Seller testSeller;
    private SellerDTO testSellerDTO;
    private List<Review> testReviews;
    private LocalDateTime testDateTime;

    @BeforeEach
    void setUp() {
        productMapper = new ProductMapper();
        testDateTime = LocalDateTime.of(2025, 7, 20, 10, 30);

        testPaymentMethods = Arrays.asList(
                PaymentMethod.builder()
                        .id("pm-001")
                        .name("Credit Card")
                        .description("Visa, Mastercard")
                        .build(),
                PaymentMethod.builder()
                        .id("pm-002")
                        .name("PayPal")
                        .description("Online payment")
                        .build()
        );

        testPaymentMethodDTOs = Arrays.asList(
                PaymentMethodDTO.builder()
                        .id("pm-001")
                        .name("Credit Card")
                        .description("Visa, Mastercard")
                        .build(),
                PaymentMethodDTO.builder()
                        .id("pm-002")
                        .name("PayPal")
                        .description("Online payment")
                        .build()
        );

        testSeller = Seller.builder()
                .id("seller-001")
                .name("Test Seller")
                .email("seller@test.com")
                .phone("1234567890")
                .rating(4.5)
                .build();

        testSellerDTO = SellerDTO.builder()
                .id("seller-001")
                .name("Test Seller")
                .email("seller@test.com")
                .phone("1234567890")
                .rating(4.5)
                .build();

        testReviews = Arrays.asList(
                Review.builder()
                        .id("rev-001")
                        .userId("user-001")
                        .userName("Test User")
                        .comment("Great product")
                        .rating(5)
                        .createdAt(testDateTime)
                        .build()
        );


        testProduct = Product.builder()
                .id("prod-001")
                .title("Test Product")
                .description("Test Description")
                .price(new BigDecimal("99.99"))
                .images(Arrays.asList("image1.jpg", "image2.jpg"))
                .paymentMethods(testPaymentMethods)
                .seller(testSeller)
                .stock(10)
                .rating(4.8)
                .reviews(testReviews)
                .build();

        testProductRequestDTO = ProductRequestDTO.builder()
                .title("Test Product")
                .description("Test Description")
                .price(new BigDecimal("99.99"))
                .images(Arrays.asList("image1.jpg", "image2.jpg"))
                .paymentMethods(testPaymentMethodDTOs)
                .seller(testSellerDTO)
                .stock(10)
                .build();
    }

    @Test
    void testToEntityShouldConvertRequestDTOToEntity() {
        Product result = productMapper.toEntity(testProductRequestDTO);

        assertNotNull(result);
        assertEquals(testProductRequestDTO.title(), result.getTitle());
        assertEquals(testProductRequestDTO.description(), result.getDescription());
        assertEquals(testProductRequestDTO.price(), result.getPrice());
        assertEquals(testProductRequestDTO.images(), result.getImages());
        assertEquals(testProductRequestDTO.stock(), result.getStock());
        assertEquals(0.0, result.getRating());
        assertTrue(result.getReviews().isEmpty());

        assertEquals(testProductRequestDTO.paymentMethods().size(), result.getPaymentMethods().size());
        for (int i = 0; i < testProductRequestDTO.paymentMethods().size(); i++) {
            PaymentMethodDTO dto = testProductRequestDTO.paymentMethods().get(i);
            PaymentMethod entity = result.getPaymentMethods().get(i);
            assertEquals(dto.id(), entity.getId());
            assertEquals(dto.name(), entity.getName());
            assertEquals(dto.description(), entity.getDescription());
        }

        assertEquals(testProductRequestDTO.seller().id(), result.getSeller().getId());
        assertEquals(testProductRequestDTO.seller().name(), result.getSeller().getName());
        assertEquals(testProductRequestDTO.seller().email(), result.getSeller().getEmail());
        assertEquals(testProductRequestDTO.seller().phone(), result.getSeller().getPhone());
        assertEquals(testProductRequestDTO.seller().rating(), result.getSeller().getRating());
    }

    @Test
    void testToEntityWhenRequestDTOIsNullShouldReturnNull() {
        Product result = productMapper.toEntity(null);

        assertNull(result);
    }

    @Test
    void testToResponseDTOShouldConvertEntityToResponseDTO() {
        ProductResponseDTO result = productMapper.toResponseDTO(testProduct);

        assertNotNull(result);
        assertEquals(testProduct.getId(), result.id());
        assertEquals(testProduct.getTitle(), result.title());
        assertEquals(testProduct.getDescription(), result.description());
        assertEquals(testProduct.getPrice(), result.price());
        assertEquals(testProduct.getImages(), result.images());
        assertEquals(testProduct.getStock(), result.stock());
        assertEquals(testProduct.getRating(), result.rating());

        assertEquals(testProduct.getPaymentMethods().size(), result.paymentMethods().size());
        for (int i = 0; i < testProduct.getPaymentMethods().size(); i++) {
            PaymentMethod entity = testProduct.getPaymentMethods().get(i);
            PaymentMethodDTO dto = result.paymentMethods().get(i);
            assertEquals(entity.getId(), dto.id());
            assertEquals(entity.getName(), dto.name());
            assertEquals(entity.getDescription(), dto.description());
        }

        assertEquals(testProduct.getSeller().getId(), result.seller().id());
        assertEquals(testProduct.getSeller().getName(), result.seller().name());
        assertEquals(testProduct.getSeller().getEmail(), result.seller().email());
        assertEquals(testProduct.getSeller().getPhone(), result.seller().phone());
        assertEquals(testProduct.getSeller().getRating(), result.seller().rating());

        assertEquals(testProduct.getReviews().size(), result.reviews().size());
        for (int i = 0; i < testProduct.getReviews().size(); i++) {
            Review entity = testProduct.getReviews().get(i);
            ReviewDTO dto = result.reviews().get(i);
            assertEquals(entity.getId(), dto.id());
            assertEquals(entity.getUserId(), dto.userId());
            assertEquals(entity.getUserName(), dto.userName());
            assertEquals(entity.getComment(), dto.comment());
            assertEquals(entity.getRating(), dto.rating());
            assertEquals(entity.getCreatedAt(), dto.createdAt());
        }
    }

    @Test
    void testToResponseDTOWhenEntityIsNullShouldReturnNull() {
        ProductResponseDTO result = productMapper.toResponseDTO(null);

        assertNull(result);
    }

    @Test
    void testToResponseDTOsShouldConvertListOfEntitiesToListOfResponseDTOs() {
        List<Product> products = Arrays.asList(testProduct, testProduct);

        List<ProductResponseDTO> result = productMapper.toResponseDTOs(products);

        assertEquals(products.size(), result.size());
        for (int i = 0; i < products.size(); i++) {
            ProductResponseDTO dto = result.get(i);
            assertEquals(products.get(i).getId(), dto.id());
            assertEquals(products.get(i).getTitle(), dto.title());
        }
    }

    @Test
    void testToResponseDTOsWhenEntitiesListIsNullShouldReturnEmptyList() {
        List<ProductResponseDTO> result = productMapper.toResponseDTOs(null);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testUpdateEntityFromDTOShouldUpdateEntityWithDTOValues() {
        Product product = Product.builder()
                .id("prod-001")
                .title("Original Title")
                .description("Original Description")
                .price(new BigDecimal("49.99"))
                .images(new ArrayList<>())
                .paymentMethods(new ArrayList<>())
                .seller(null)
                .stock(5)
                .rating(4.0)
                .reviews(Arrays.asList(testReviews.get(0)))
                .build();

        Product result = productMapper.updateEntityFromDTO(product, testProductRequestDTO);

        assertEquals("prod-001", result.getId());
        assertEquals(testProductRequestDTO.title(), result.getTitle());
        assertEquals(testProductRequestDTO.description(), result.getDescription());
        assertEquals(testProductRequestDTO.price(), result.getPrice());
        assertEquals(testProductRequestDTO.images(), result.getImages());
        assertEquals(testProductRequestDTO.stock(), result.getStock());
        assertEquals(4.0, result.getRating());
        assertEquals(1, result.getReviews().size());

        assertEquals(testProductRequestDTO.paymentMethods().size(), result.getPaymentMethods().size());
        
        assertNotNull(result.getSeller());
        assertEquals(testProductRequestDTO.seller().id(), result.getSeller().getId());
        assertEquals(testProductRequestDTO.seller().name(), result.getSeller().getName());
    }

    @Test
    void testUpdateEntityFromDTOWhenEntityOrDTOIsNullShouldReturnOriginalEntity() {
        Product product = Product.builder().id("prod-001").title("Original Title").build();

        Product result1 = productMapper.updateEntityFromDTO(product, null);
        
        Product result2 = productMapper.updateEntityFromDTO(null, testProductRequestDTO);

        assertEquals(product, result1);
        assertNull(result2);
    }
}