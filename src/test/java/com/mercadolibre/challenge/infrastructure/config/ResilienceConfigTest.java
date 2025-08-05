package com.mercadolibre.challenge.infrastructure.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ResilienceConfigTest {

    @Test
    void testCircuitBreakerConfigShouldCreateBean() {
        ResilienceConfig resilienceConfig = new ResilienceConfig();

        CircuitBreakerConfig config = resilienceConfig.circuitBreakerConfig();

        assertNotNull(config);
    }

    @Test
    void testRetryConfigShouldCreateBean() {
        ResilienceConfig resilienceConfig = new ResilienceConfig();

        RetryConfig config = resilienceConfig.retryConfig();

        assertNotNull(config);
    }

    @Test
    void testRateLimiterConfigShouldCreateBean() {
        ResilienceConfig resilienceConfig = new ResilienceConfig();

        RateLimiterConfig config = resilienceConfig.rateLimiterConfig();

        assertNotNull(config);
    }

    @Test
    void testTimeLimiterConfigShouldCreateBean() {
        ResilienceConfig resilienceConfig = new ResilienceConfig();

        TimeLimiterConfig config = resilienceConfig.timeLimiterConfig();

        assertNotNull(config);
    }
}