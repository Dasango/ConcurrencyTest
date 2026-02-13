package com.uce.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ApplicationScoped
@Named("ProcesamientoLotes")
public class ExecutorConfig {
    private ExecutorService executorService;
    @PostConstruct
    public void init(){
        this.executorService=
                Executors.newFixedThreadPool(
                        Runtime.getRuntime().availableProcessors()
                );
    }

    @ApplicationScoped
    @Produces
    public ExecutorService getExecutorService() {
        return executorService;
    }

    @PreDestroy
    public void close(){
        this.executorService.shutdown();
    }
}
