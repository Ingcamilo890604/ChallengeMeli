package com.mercadolibre.challenge.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SellerTest {

    @Test
    void testSellerBuilder() {
        // Arrange
        String id = "seller-001";
        String name = "Test Seller";
        String email = "seller@test.com";
        String phone = "123456789";
        Double rating = 4.5;

        // Act
        Seller seller = Seller.builder()
                .id(id)
                .name(name)
                .email(email)
                .phone(phone)
                .rating(rating)
                .build();

        // Assert
        assertEquals(id, seller.getId());
        assertEquals(name, seller.getName());
        assertEquals(email, seller.getEmail());
        assertEquals(phone, seller.getPhone());
        assertEquals(rating, seller.getRating());
    }

    @Test
    void testGettersAndSetters() {
        // Arrange
        Seller seller = new Seller();
        String id = "seller-002";
        String name = "Another Seller";
        String email = "another@test.com";
        String phone = "987654321";
        Double rating = 3.8;

        // Act
        seller.setId(id);
        seller.setName(name);
        seller.setEmail(email);
        seller.setPhone(phone);
        seller.setRating(rating);

        // Assert
        assertEquals(id, seller.getId());
        assertEquals(name, seller.getName());
        assertEquals(email, seller.getEmail());
        assertEquals(phone, seller.getPhone());
        assertEquals(rating, seller.getRating());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Seller seller1 = Seller.builder()
                .id("seller-001")
                .name("Test Seller")
                .email("seller@test.com")
                .phone("123456789")
                .rating(4.5)
                .build();

        Seller seller2 = Seller.builder()
                .id("seller-001")
                .name("Test Seller")
                .email("seller@test.com")
                .phone("123456789")
                .rating(4.5)
                .build();

        Seller seller3 = Seller.builder()
                .id("seller-002")
                .name("Different Seller")
                .email("different@test.com")
                .phone("987654321")
                .rating(3.8)
                .build();

        // Assert
        assertEquals(seller1, seller2);
        assertEquals(seller1.hashCode(), seller2.hashCode());
        assertNotEquals(seller1, seller3);
        assertNotEquals(seller1.hashCode(), seller3.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        Seller seller = Seller.builder()
                .id("seller-001")
                .name("Test Seller")
                .email("seller@test.com")
                .build();

        // Act
        String toString = seller.toString();

        // Assert
        assertTrue(toString.contains("id=seller-001"));
        assertTrue(toString.contains("name=Test Seller"));
        assertTrue(toString.contains("email=seller@test.com"));
    }
}