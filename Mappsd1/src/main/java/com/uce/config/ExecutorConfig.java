package com.uce.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jakarta.persistence.Persistence;

@ApplicationScoped
public class ExecutorConfig {

    private ExecutorService executor;
    @PostConstruct
    public void init(){
        this.executor = Executors
                .newFixedThreadPool(
                        Runtime.getRuntime().availableProcessors()
                );
    }

    @Produces
    public ExecutorService getExecutor() {
        return executor;
    }

    @PreDestroy
    public void close(){
        this.executor.shutdown();
    }
}
