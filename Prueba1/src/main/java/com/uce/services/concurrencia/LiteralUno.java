package com.uce.services.concurrencia;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManagerFactory;

import java.util.concurrent.ExecutorService;

@ApplicationScoped
public class LiteralUno {
    @Inject
    private EntityManagerFactory emf;
    @Inject
    private ExecutorService executorService;
    public void run(){

    }
}
