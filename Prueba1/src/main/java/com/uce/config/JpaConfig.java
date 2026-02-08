package com.uce.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ApplicationScoped
public class JpaConfig {

    private EntityManagerFactory emf;

    @PostConstruct
    public void init(){
        this.emf= Persistence.createEntityManagerFactory("PU");
    }

    @Produces
    public EntityManagerFactory getEmf(){
        return this.emf;
    }
    @PreDestroy
    public void close(){
        if (this.emf.isOpen()){
            this.emf.close();
        }
    }
}
