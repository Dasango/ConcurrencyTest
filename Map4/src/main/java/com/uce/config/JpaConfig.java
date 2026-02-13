package com.uce.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JpaConfig {
    private EntityManagerFactory entityManagerFactory;
    @PostConstruct
    public void init(){
        this.entityManagerFactory=
                Persistence.createEntityManagerFactory("PU");

    }

    @Produces
    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    @PreDestroy
    public void close(){
        if (this.entityManagerFactory.isOpen()){
            this.entityManagerFactory.close();
        }
    }
}