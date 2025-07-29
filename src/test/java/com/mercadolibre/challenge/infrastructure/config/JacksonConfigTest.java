package com.mercadolibre.challenge.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class JacksonConfigTest {


    @Test
    void objectMapper_shouldSerializeLocalDateTime() throws Exception {
        // Arrange
        JacksonConfig jacksonConfig = new JacksonConfig();
        ObjectMapper objectMapper = jacksonConfig.objectMapper();
        LocalDateTime dateTime = LocalDateTime.of(2025, 7, 26, 10, 30, 0);
        
        // Act
        String json = objectMapper.writeValueAsString(dateTime);
        
        // Assert
        assertTrue(json.contains("2025-07-26T10:30:00"));
        assertFalse(json.contains("timestamp"));
    }
}