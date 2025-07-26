package com.mercadolibre.challenge.infrastructure.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class WebConfigTest {

    @Test
    void corsConfigurer_shouldReturnWebMvcConfigurer() {
        // Arrange
        WebConfig webConfig = new WebConfig();

        // Act
        WebMvcConfigurer configurer = webConfig.corsConfigurer();

        // Assert
        assertNotNull(configurer);
    }
}