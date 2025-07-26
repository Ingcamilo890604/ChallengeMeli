package com.mercadolibre.challenge.infrastructure.config;

import org.junit.jupiter.api.Test;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class AsyncConfigTest {

    @Test
    void virtualThreadTaskExecutor_shouldReturnTaskExecutorAdapter() {
        // Arrange
        AsyncConfig asyncConfig = new AsyncConfig();

        // Act
        AsyncTaskExecutor executor = asyncConfig.virtualThreadTaskExecutor();

        // Assert
        assertNotNull(executor);
        assertInstanceOf(TaskExecutorAdapter.class, executor);
    }

    @Test
    void configureAsyncSupport_shouldSetTaskExecutor() {
        // Arrange
        AsyncConfig asyncConfig = new AsyncConfig();
        AsyncSupportConfigurer configurer = mock(AsyncSupportConfigurer.class);

        // Act
        asyncConfig.configureAsyncSupport(configurer);

        // Assert
        verify(configurer).setTaskExecutor(any(TaskExecutorAdapter.class));
    }
}