package com.davidcode;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class Exercise6 {

    public static void main(String[] args) {
        Logger.getLogger("").setLevel(Level.SEVERE);
        try (SeContainer container = SeContainerInitializer.newInstance()
                .addBeanClasses(EntityRepository.class)
                .initialize()) {
            EntityRepository repo = container.select(EntityRepository.class).get();

            CompletableFuture<Void> saveAll = CompletableFuture.runAsync(() -> {
                for (int i = 0; i < 1000; i++) {
                    repo.save(new Entity(i, "Entity-" + i));
                }
                System.out.println("CSV generado con exito.");
            });


            CompletableFuture<Long> countTask = saveAll.thenApplyAsync(v -> repo.countLines());
            CompletableFuture<Integer> maxTask = saveAll.thenApplyAsync(v -> repo.getMaxId());
            CompletableFuture<Integer> minTask = saveAll.thenApplyAsync(v -> repo.getMinId());

            CompletableFuture.allOf(countTask, maxTask, minTask).join();

            System.out.println("--- RESULTADOS ---");
            System.out.println("Total registros: " + countTask.join());
            System.out.println("ID Maximo: " + maxTask.join());
            System.out.println("ID Minimo: " + minTask.join());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- UTILS ---
    @ApplicationScoped
    public static class EntityRepository {
        private final String FILE_NAME = "data.csv";
        private final String BASE_PATH = "CompletableFuturesExercises/src/main/resources/";
        @PostConstruct
        private void init(){
            try {
                Files.createDirectories(Path.of(BASE_PATH));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void save(Entity obj) {
            try (FileWriter writer = new FileWriter(BASE_PATH+ FILE_NAME, true)) {
                writer.append(String.valueOf(obj.id)).append("\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public long countLines() {
            try (Stream<String> lines = Files.lines(Path.of(BASE_PATH+ FILE_NAME))) {
                return lines.count();
            } catch (IOException e) { return 0; }
        }

        public int getMaxId() {
            try (Stream<String> lines = Files.lines(Path.of(BASE_PATH+ FILE_NAME))) {
                return lines.mapToInt(Integer::parseInt).max().orElse(0);
            } catch (IOException e) { return 0; }
        }

        public int getMinId() {
            try (Stream<String> lines = Files.lines(Path.of(BASE_PATH+ FILE_NAME))) {
                return lines.mapToInt(Integer::parseInt).min().orElse(0);
            } catch (IOException e) { return 0; }
        }
    }

    public record Entity(int id, String name) {}
}