package com.mercadolibre.challenge.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void testProductBuilder() {
        // Arrange
        String id = "prod-001";
        String title = "Test Product";
        String description = "Test Description";
        BigDecimal price = new BigDecimal("99.99");
        List<String> images = Arrays.asList("image1.jpg", "image2.jpg");
        List<PaymentMethod> paymentMethods = Arrays.asList(
                PaymentMethod.builder().id("pm-001").name("Credit Card").description("Visa, Mastercard").build(),
                PaymentMethod.builder().id("pm-002").name("PayPal").description("Online payment").build()
        );
        Seller seller = Seller.builder().id("seller-001").name("Test Seller").email("seller@test.com").phone("123456789").rating(4.5).build();
        Integer stock = 10;
        Double rating = 4.8;
        List<Review> reviews = Arrays.asList(
                Review.builder().id("rev-001").userId("user-001").userName("Test User").comment("Great product").rating(5).build()
        );

        // Act
        Product product = Product.builder()
                .id(id)
                .title(title)
                .description(description)
                .price(price)
                .images(images)
                .paymentMethods(paymentMethods)
                .seller(seller)
                .stock(stock)
                .rating(rating)
                .reviews(reviews)
                .build();

        // Assert
        assertEquals(id, product.getId());
        assertEquals(title, product.getTitle());
        assertEquals(description, product.getDescription());
        assertEquals(price, product.getPrice());
        assertEquals(images, product.getImages());
        assertEquals(paymentMethods, product.getPaymentMethods());
        assertEquals(seller, product.getSeller());
        assertEquals(stock, product.getStock());
        assertEquals(rating, product.getRating());
        assertEquals(reviews, product.getReviews());
    }

    @Test
    void testGettersAndSetters() {
        // Arrange
        Product product = new Product();
        String id = "prod-002";
        String title = "Another Product";
        String description = "Another Description";
        BigDecimal price = new BigDecimal("199.99");
        List<String> images = new ArrayList<>();
        List<PaymentMethod> paymentMethods = new ArrayList<>();
        Seller seller = new Seller();
        Integer stock = 20;
        Double rating = 3.5;
        List<Review> reviews = new ArrayList<>();

        // Act
        product.setId(id);
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setImages(images);
        product.setPaymentMethods(paymentMethods);
        product.setSeller(seller);
        product.setStock(stock);
        product.setRating(rating);
        product.setReviews(reviews);

        // Assert
        assertEquals(id, product.getId());
        assertEquals(title, product.getTitle());
        assertEquals(description, product.getDescription());
        assertEquals(price, product.getPrice());
        assertEquals(images, product.getImages());
        assertEquals(paymentMethods, product.getPaymentMethods());
        assertEquals(seller, product.getSeller());
        assertEquals(stock, product.getStock());
        assertEquals(rating, product.getRating());
        assertEquals(reviews, product.getReviews());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Product product1 = Product.builder()
                .id("prod-001")
                .title("Test Product")
                .description("Test Description")
                .price(new BigDecimal("99.99"))
                .build();

        Product product2 = Product.builder()
                .id("prod-001")
                .title("Test Product")
                .description("Test Description")
                .price(new BigDecimal("99.99"))
                .build();

        Product product3 = Product.builder()
                .id("prod-002")
                .title("Different Product")
                .description("Different Description")
                .price(new BigDecimal("199.99"))
                .build();

        // Assert
        assertEquals(product1, product2);
        assertEquals(product1.hashCode(), product2.hashCode());
        assertNotEquals(product1, product3);
        assertNotEquals(product1.hashCode(), product3.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        Product product = Product.builder()
                .id("prod-001")
                .title("Test Product")
                .build();

        // Act
        String toString = product.toString();

        // Assert
        assertTrue(toString.contains("id=prod-001"));
        assertTrue(toString.contains("title=Test Product"));
    }
}