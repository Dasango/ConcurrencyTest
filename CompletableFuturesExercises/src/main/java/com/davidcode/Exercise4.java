package com.davidcode;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Exercise4 {
    /**
     * EXERCISE 4: CSV Processing & Statistics
     * * 1. Data Generation:
     * Generate 3 separate CSV files. Populate each with 10,000 random integers
     * between 0 and 999,999.
     * * 2. Processing:
     * Read the CSV files (parse them).
     * * 3. Analysis:
     * - Identify and print the top 3 largest numbers from each file.
     * - Calculate the average (mean) of these top 3 numbers for each file.
     * * 4. Ranking:
     * Classify/Rank the CSV files based on which one has the highest average
     * of its top 3 values.
     */

    private static final String DATA_DIR = "CompletableFuturesExercises/src/main/resources/data";

    public static void main(String[] args) {
        try {
            Files.createDirectories(Path.of(DATA_DIR)); }
        catch (IOException e) {
            e.printStackTrace();
        }

        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<String> fileNames = List.of("1.csv", "2.csv", "3.csv");

        List<CompletableFuture<Path>> generationFutures = fileNames.stream()
                .map(fileName -> CompletableFuture.supplyAsync(() -> generateCsv(fileName), executor))
                .toList();

        CompletableFuture<Void> allFilesCreated = CompletableFuture.allOf(
                generationFutures.toArray(new CompletableFuture[0])
        );

        CompletableFuture<List<FileStatsDTO>> analysisFuture = allFilesCreated.thenApplyAsync(v -> {
            List<Path> paths = generationFutures.stream().map(CompletableFuture::join).toList();

            List<CompletableFuture<FileStatsDTO>> statsFutures = paths.stream()
                    .map(path -> CompletableFuture.supplyAsync(() -> analyzeFile(path), executor))
                    .toList();

            return statsFutures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());
        }, executor);

        analysisFuture.thenAccept(statsList -> {
            statsList.sort(Comparator.comparingDouble(FileStatsDTO::average).reversed());

            statsList.forEach(stat ->
                    System.out.printf("File: %s | Top 3: %s | Avg: %.2f \n",
                            stat.fileName(), stat.top3(), stat.average())
            );

            FileStatsDTO winner = statsList.get(0);
            System.out.println("WINNER: " + winner.fileName() + " with " + winner.average());
        }).join();

        executor.shutdown();
    }

    private static Path generateCsv(String fileName) {
        Path path = Path.of(DATA_DIR, fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            Random random = new Random();
            for (int i = 0; i < 10000; i++) {
                writer.write(String.valueOf(random.nextInt(0, 999999)));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return path;
    }

    private static FileStatsDTO analyzeFile(Path path) {
        try (Stream<String> lines = Files.lines(path)) {
            List<Integer> top3 = lines
                    .map(Integer::valueOf)
                    .sorted(Comparator.reverseOrder())
                    .limit(3)
                    .toList();

            double avg = top3.stream().mapToInt(Integer::intValue).average().orElse(0.0);

            return new FileStatsDTO(path.getFileName().toString(), top3, avg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    record FileStatsDTO(String fileName, List<Integer> top3, double average) {}
}