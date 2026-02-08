package com.uce.services.concurrencia.tasks;

import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

public class ImportCSVTask<T> implements Runnable{


    private EntityManagerFactory emf;

    private final String BASE_PATH = "C:\\Users\\David\\git\\ConcurrencyTest\\Libray\\src\\main\\resources\\data\\";

    private String path;

    private Function<String[],T> fn;

    public ImportCSVTask(EntityManagerFactory emf, Function<String[],T> fn, String path) {
        this.fn = fn;
        this.emf = emf;
        this.path = path;
    }

    @Override
    public void run() {
        Path ruta = Paths.get(BASE_PATH+path);

        try {
            Files.lines(ruta)
                    .map(line ->{
                        return line.split(",");
                    })
                    .map(arg->{
                        return this.fn.apply(arg);
                    })
                    .forEach((a)->{
                        this.emf
                                .runInTransaction(em->{
                                    em.persist(em);
                                });
                    });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
