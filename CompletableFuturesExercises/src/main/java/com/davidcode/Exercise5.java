package com.davidcode;

import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Exercise5 {

    /**
     * EXERCISE 5: CDI Events & Async Handling
     * Make a 'MarketSensor' that detects stock price changes and fires a CDI Event.
     * Make a 'NewsAgency' that listens (@Observes) to this event.
     */
    public static void main(String[] args) {
        System.out.println("[Start]");

        try (SeContainer container = SeContainerInitializer.newInstance()
                .addBeanClasses(MarketSensor.class, NewsAgency.class)
                .initialize()) {

            MarketSensor sensor = container.select(MarketSensor.class).get();

            System.out.println("[MAIN] Triggering price update...");
            sensor.updatePrice("NVDA", 135.50);
            System.out.println("[MAIN] Control returned to Main.");

            Thread.sleep(4000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // --- UTILS / BEANS ---

    // 1. The Event Payload (POJO)
    public static class StockEvent {
        public String ticker;
        public double newPrice;

        public StockEvent(String ticker, double newPrice) {
            this.ticker = ticker;
            this.newPrice = newPrice;
        }
    }

    // 2. The Producer (Sensor)
    @Singleton
    public static class MarketSensor {

        @Inject
        private Event<StockEvent> eventManager;

        public void updatePrice(String ticker, double price) {
            System.out.println("[SENSOR] Price change detected for " + ticker);

            // Fires the event synchronously to all observers
            eventManager.fire(new StockEvent(ticker, price));

            System.out.println("[SENSOR] Event processing finished.");
        }
    }

    // 3. The Consumer (Observer)
    @Singleton
    public static class NewsAgency {

        public void onPriceChange(@Observes StockEvent event) {
            long start = System.currentTimeMillis();

            System.out.println("   -> [AUDIT] Logged change for " + event.ticker);

            CompletableFuture.runAsync(() -> {
                System.out.println("      -> [EMAIL] Sending emails (Start)...");
                simulateDelay(3000);
                System.out.println("      -> [EMAIL] Emails sent (End).");
            });


            System.out.println("   -> [OBSERVER] Method finished in " + (System.currentTimeMillis() - start) + "ms");
        }

        private void simulateDelay(int ms) {
            try { TimeUnit.MILLISECONDS.sleep(ms); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }
}