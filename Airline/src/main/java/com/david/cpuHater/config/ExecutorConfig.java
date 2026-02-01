package com.david.cpuHater.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ApplicationScoped
public class ExecutorConfig {

    private ExecutorService executorService;

    @PostConstruct
    public void init(){
        this.executorService = Optional.of(Runtime.getRuntime().availableProcessors())
                .map(Executors::newFixedThreadPool)
                .orElseGet(() -> Executors.newFixedThreadPool(1));
    }
    @Produces
    @ApplicationScoped
    public ExecutorService getExecutorService(){
        return this.executorService;
    }
    @PreDestroy
    public void shutdown(){
        this.executorService.shutdown();
    }
}
