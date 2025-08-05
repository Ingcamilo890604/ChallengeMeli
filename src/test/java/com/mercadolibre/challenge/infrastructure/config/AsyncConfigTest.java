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
    void testVirtualThreadTaskExecutorShouldReturnTaskExecutorAdapter() {
        AsyncConfig asyncConfig = new AsyncConfig();

        AsyncTaskExecutor executor = asyncConfig.virtualThreadTaskExecutor();

        assertNotNull(executor);
        assertInstanceOf(TaskExecutorAdapter.class, executor);
    }

    @Test
    void testConfigureAsyncSupportShouldSetTaskExecutor() {
        AsyncConfig asyncConfig = new AsyncConfig();
        AsyncSupportConfigurer configurer = mock(AsyncSupportConfigurer.class);

        asyncConfig.configureAsyncSupport(configurer);

        verify(configurer).setTaskExecutor(any(TaskExecutorAdapter.class));
    }
}