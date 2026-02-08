package com.uce.services.concurrencia;

import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LiteralDosTask implements Runnable {

    String path;
    EntityManagerFactory entityManagerFactory;

    @Override
    public void run() {
        Path ruta = Paths.get(path);

        try {
            Files.lines(ruta);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
