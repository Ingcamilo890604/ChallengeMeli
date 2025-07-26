package com.mercadolibre.challenge.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class JacksonConfigTest {

    @Test
    void objectMapper_shouldBeConfiguredCorrectly() {
        // Arrange
        JacksonConfig jacksonConfig = new JacksonConfig();

        // Act
        ObjectMapper objectMapper = jacksonConfig.objectMapper();

        // Assert
        assertNotNull(objectMapper);
        
        // Verify JavaTimeModule is registered
        assertTrue(objectMapper.getRegisteredModuleIds().contains(JavaTimeModule.class.getName()));
        
        // Verify WRITE_DATES_AS_TIMESTAMPS is disabled
        assertFalse(objectMapper.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS));
        
        // Verify INDENT_OUTPUT is enabled
        assertTrue(objectMapper.isEnabled(SerializationFeature.INDENT_OUTPUT));
    }
    
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