package com.mercadolibre.challenge.infrastructure.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ResilienceConfigTest {

    @Test
    void circuitBreakerConfig_shouldCreateBean() {
        // Arrange
        ResilienceConfig resilienceConfig = new ResilienceConfig();

        // Act
        CircuitBreakerConfig config = resilienceConfig.circuitBreakerConfig();

        // Assert
        assertNotNull(config);
    }

    @Test
    void retryConfig_shouldCreateBean() {
        // Arrange
        ResilienceConfig resilienceConfig = new ResilienceConfig();

        // Act
        RetryConfig config = resilienceConfig.retryConfig();

        // Assert
        assertNotNull(config);
    }

    @Test
    void rateLimiterConfig_shouldCreateBean() {
        // Arrange
        ResilienceConfig resilienceConfig = new ResilienceConfig();

        // Act
        RateLimiterConfig config = resilienceConfig.rateLimiterConfig();

        // Assert
        assertNotNull(config);
    }

    @Test
    void timeLimiterConfig_shouldCreateBean() {
        // Arrange
        ResilienceConfig resilienceConfig = new ResilienceConfig();

        // Act
        TimeLimiterConfig config = resilienceConfig.timeLimiterConfig();

        // Assert
        assertNotNull(config);
    }
}