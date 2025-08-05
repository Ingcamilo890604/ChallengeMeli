package com.mercadolibre.challenge.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReviewTest {

    @Test
    void testReviewBuilder() {
        String id = "rev-001";
        String userId = "user-001";
        String userName = "Test User";
        String comment = "Great product";
        Integer rating = 5;
        LocalDateTime createdAt = LocalDateTime.of(2025, 7, 20, 10, 30);

        Review review = Review.builder()
                .id(id)
                .userId(userId)
                .userName(userName)
                .comment(comment)
                .rating(rating)
                .createdAt(createdAt)
                .build();

        assertEquals(id, review.getId());
        assertEquals(userId, review.getUserId());
        assertEquals(userName, review.getUserName());
        assertEquals(comment, review.getComment());
        assertEquals(rating, review.getRating());
        assertEquals(createdAt, review.getCreatedAt());
    }

    @Test
    void testGettersAndSetters() {
        Review review = new Review();
        String id = "rev-002";
        String userId = "user-002";
        String userName = "Another User";
        String comment = "Good product";
        Integer rating = 4;
        LocalDateTime createdAt = LocalDateTime.of(2025, 7, 21, 15, 45);

        review.setId(id);
        review.setUserId(userId);
        review.setUserName(userName);
        review.setComment(comment);
        review.setRating(rating);
        review.setCreatedAt(createdAt);

        assertEquals(id, review.getId());
        assertEquals(userId, review.getUserId());
        assertEquals(userName, review.getUserName());
        assertEquals(comment, review.getComment());
        assertEquals(rating, review.getRating());
        assertEquals(createdAt, review.getCreatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime createdAt = LocalDateTime.of(2025, 7, 20, 10, 30);
        
        Review review1 = Review.builder()
                .id("rev-001")
                .userId("user-001")
                .userName("Test User")
                .comment("Great product")
                .rating(5)
                .createdAt(createdAt)
                .build();

        Review review2 = Review.builder()
                .id("rev-001")
                .userId("user-001")
                .userName("Test User")
                .comment("Great product")
                .rating(5)
                .createdAt(createdAt)
                .build();

        Review review3 = Review.builder()
                .id("rev-002")
                .userId("user-002")
                .userName("Different User")
                .comment("Good product")
                .rating(4)
                .createdAt(LocalDateTime.of(2025, 7, 21, 15, 45))
                .build();

        assertEquals(review1, review2);
        assertEquals(review1.hashCode(), review2.hashCode());
        assertNotEquals(review1, review3);
        assertNotEquals(review1.hashCode(), review3.hashCode());
    }

    @Test
    void testToString() {
        Review review = Review.builder()
                .id("rev-001")
                .userId("user-001")
                .userName("Test User")
                .rating(5)
                .build();

        String toString = review.toString();

        assertTrue(toString.contains("id=rev-001"));
        assertTrue(toString.contains("userId=user-001"));
        assertTrue(toString.contains("userName=Test User"));
        assertTrue(toString.contains("rating=5"));
    }
}