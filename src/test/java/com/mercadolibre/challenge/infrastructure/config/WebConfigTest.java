package com.mercadolibre.challenge.infrastructure.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class WebConfigTest {

    @Test
    void testCorsConfigurerShouldReturnWebMvcConfigurer() {
        WebConfig webConfig = new WebConfig();

        WebMvcConfigurer configurer = webConfig.corsConfigurer();

        assertNotNull(configurer);
    }
}