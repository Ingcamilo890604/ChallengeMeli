package com.mercadolibre.challenge.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.Executors;

/**
 * Configuration for asynchronous processing using virtual threads
 */
@Configuration
@EnableAsync
public class AsyncConfig implements WebMvcConfigurer {

    /**
     * Configure async support for Spring MVC
     * This enables virtual threads for handling HTTP requests
     */
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setTaskExecutor(virtualThreadTaskExecutor());
    }

    /**
     * Create a task executor that uses virtual threads
     * @return AsyncTaskExecutor using virtual threads
     */
    @Bean
    public AsyncTaskExecutor virtualThreadTaskExecutor() {
        return new TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor());
    }
}