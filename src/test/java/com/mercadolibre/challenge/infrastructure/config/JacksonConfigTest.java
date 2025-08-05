package com.mercadolibre.challenge.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class JacksonConfigTest {


    @Test
    void testObjectMapperShouldSerializeLocalDateTime() throws Exception {
        JacksonConfig jacksonConfig = new JacksonConfig();
        ObjectMapper objectMapper = jacksonConfig.objectMapper();
        LocalDateTime dateTime = LocalDateTime.of(2025, 7, 26, 10, 30, 0);
        
        String json = objectMapper.writeValueAsString(dateTime);
        
        assertTrue(json.contains("2025-07-26T10:30:00"));
        assertFalse(json.contains("timestamp"));
    }
}