package com.uce.services.concurrencia;

import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

public class ConcurrenciaTask1<T> implements Runnable {

    private final String BASE_PATH = "C:\\Users\\David\\git\\java\\ConcurrencyTest\\Prueba1\\src\\main\\resources\\";
    private String path;
    private EntityManagerFactory entityManagerFactory;
    private Function<String[],T > fn;

    public ConcurrenciaTask1(String path, EntityManagerFactory entityManagerFactory, Function<String[], T> fn) {
        this.path = path;
        this.entityManagerFactory = entityManagerFactory;
        this.fn = fn;
    }

    @Override
    public void run() {
        Path ruta = Paths.get(BASE_PATH+path);
        try {
            Files.lines(ruta)
                    .map(lines -> lines.split(","))
                    .map(tokens-> this.fn.apply(tokens))
                    .forEach(item -> this.entityManagerFactory.runInTransaction( em ->{
                        em.persist(item);
                        }
                    ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
