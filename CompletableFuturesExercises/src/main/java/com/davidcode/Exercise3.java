package com.davidcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Exercise3 {

    /**
     * CHALLENGE:
     * 1. Fetch price from Binance, Coinbase, and Kraken in parallel.
     * 2. If a request takes > 2s, ignore it (Timeout).
     * 3. If a request fails (Exception), ignore it.
     * 4. Calculate (Max - Min) with the surviving prices.
     */

    public static void main(String[] args) {
        ExecutorService executorService= Executors.newFixedThreadPool(10);

        List<String> servers = Arrays.asList("Binance", "Coinbase","Kraken");

        List<CompletableFuture<Double>> fetchPrices = servers.stream()
                .map(server ->{
                    return CompletableFuture.supplyAsync(()->{
                        return Exercise3.getPriceFrom(server);
                    }, executorService)
                            .completeOnTimeout(
                                    Double.MIN_VALUE,
                                    2,
                                    TimeUnit.SECONDS
                            )
                            .exceptionally(ex ->{
                                return Double.MIN_VALUE;
                            })
                            ;
                })
                .toList();

        CompletableFuture<Void> allDone = CompletableFuture.allOf(
                fetchPrices.toArray(new CompletableFuture[0])
        );

        CompletableFuture<List<Double>> getMinMax = allDone.thenApply(v ->{
           var prices = fetchPrices.stream()
                   .map(CompletableFuture::join)
                   .filter(price -> !price.equals(Double.MIN_VALUE))
                   .collect(Collectors.summarizingDouble(Double::doubleValue));
           return Arrays.asList(prices.getMax(),prices.getMin());
        });

        var prices = getMinMax.join();
        System.out.println("Final Min and Max prices: "+ prices.toString());

        executorService.shutdown();

    }

    public static double getPriceFrom(String exchangeName) {
        System.out.println("Asking " + exchangeName + "...");
        sleepRandom();

        // Simulates random failure
        if (new Random().nextDouble() > 0.9) {
            throw new RuntimeException("503 Service Unavailable");
        }

        double price = 3000 + new Random().nextDouble() * 100;
        System.out.println(exchangeName + " answered: " + String.format("%.2f", price));
        return price;
    }

    public static void sleepRandom() {
        try {
            Thread.sleep(new Random().nextInt(500, 4500));
        } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}