package com.davidcode;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Exercice2 {
    /**
     * Exercise 2: Investment Portfolio (High Concurrency)
     * 1. Fetch User (Optional<User>) asynchronously.
     * 2. If present, get stock list (e.g., AAPL, NVDA, TSLA).
     * 3. Quote EACH stock in parallel threads. Use 0.0 if fetch fails.
     * 4. Simultaneously, fetch 'Market Risk' factor in a separate thread.
     * 5. Combine all: (Sum of prices * Risk) and display Total.
     */
    public static void main(String[] args) {
        System.out.println("---- STARTING PORTFOLIO CALCULATOR ----");
        ExecutorService executor = Executors.newFixedThreadPool(11);

        CompletableFuture<Optional<AppUser>> futureUser = CompletableFuture.supplyAsync(() ->
                new AppUserRepository().findByIdSync(1L), executor
        );

        CompletableFuture<Double> futureRisk = CompletableFuture.supplyAsync(MarketData::getMarketRisk, executor
        );

        futureUser.thenComposeAsync(optUser -> {

                    return optUser.map(user -> {
                        System.out.println("User found: " + user.getId() + ". Fetching stocks...");

                        List<CompletableFuture<Double>> priceFutures = user.getStocksList().stream()
                                .map(stock -> StockMarket.getPrice(stock, executor))
                                .toList();

                        CompletableFuture<Void> allFuturesDone = CompletableFuture.allOf(
                                priceFutures.toArray(new CompletableFuture[0])
                        );

                        return allFuturesDone.thenCombine(futureRisk, (voidResult, risk) -> {

                            double sumPrices = priceFutures.stream()
                                    .mapToDouble(CompletableFuture::join)
                                    .sum();

                            System.out.println("Subtotal Prices: " + sumPrices);
                            System.out.println("Market Risk applied: " + risk);

                            return sumPrices * risk;
                        });

                    }).orElseGet(() -> CompletableFuture.completedFuture(0.0));

                }, executor)
                .thenAccept(finalTotal -> System.out.println(">>> TOTAL: $" +  finalTotal))
                .exceptionally(ex -> {
                    System.err.println("Error: " + ex.getMessage());
                    return null;
                });


        sleep(10000);
        executor.shutdown();
    }


    public static class AppUser {
        private Long id;
        private List<String> stocks;

        public AppUser(Long id, String... stocks) {
            this.id = id;
            this.stocks = Arrays.asList(stocks);
        }
        public Long getId() { return id; }
        public List<String> getStocksList() { return stocks; }
    }

    public static class AppUserRepository {

        public Optional<AppUser> findByIdSync(Long id) {
            sleep(500);
            return (id == 1) ? Optional.of(new AppUser(1L, "AAPL", "TSLA", "NVDA", "AMZN", "MSFT")) : Optional.empty();
        }
    }

    public static class StockMarket {
        public static CompletableFuture<Double> getPrice(String stock, ExecutorService ex) {

            return CompletableFuture.supplyAsync(() -> {
                System.out.println("Fetching price for: " + stock + " on " + Thread.currentThread().getName());
                sleep(new Random().nextInt(3000, 10000));
                double price = new Random().nextDouble(100, 500);
                System.out.println("Fetched on " + Thread.currentThread().getName());
                return price;
            }, ex).exceptionally(e -> {
                System.err.println("Error fetching " + stock + ", counting as 0.0");
                return 0.0;
            });
        }
    }

    public static class MarketData {
        public static Double getMarketRisk() {
            sleep(1500);
            System.out.println("Risk factor calculated.");
            return 0.95;
        }
    }

    public static void sleep(int ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}