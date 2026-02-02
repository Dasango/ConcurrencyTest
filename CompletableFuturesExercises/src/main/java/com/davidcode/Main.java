package com.davidcode;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    /**
     * Ejercio1:
     * 1. Procesamiento de Órdenes
     * 2. Buscar un pedido en una base de datos simulada (usando Optional).
     * 3. Calcular el impuesto de forma asíncrona (get it from a simulated async api).
     * 4. Generar un recibo combinando los resultados.
     * 5. Cerrar el servicio de ejecución al finalizar.
     */
    public static void main(String[] args) {
        System.out.println("----- STARTS (Thread: " + Thread.currentThread().getName() + ") ------");

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Integer pedidoId = 1;

        CompletableFuture<Optional<Order>> futureOrder = CompletableFuture.supplyAsync(() ->
                OrderRepository.findById(pedidoId), executor);

        CompletableFuture<Double> futureTax = CompletableFuture.supplyAsync(() -> {
            sleep(5000);
            System.out.println("Tax rate fetched: 15%");
            return 0.15;
        }, executor);

        var process = futureOrder.thenCombineAsync(futureTax, (optOrder, taxRate) -> {
                    return optOrder.map(order -> {
                        System.out.println("Calculating final price for: " + order.getName());
                        return order.getPrice() * (1 + taxRate);
                    });
                }, executor)
                .thenAccept(optFinalPrice -> {
                    optFinalPrice.ifPresentOrElse(
                            price -> System.out.println("SUCCESS: Final price with taxes: " + price),
                            () -> System.out.println("NOT FOUND: Order does not exist.")
                    );
                })
                .exceptionally(e -> {
                    System.err.println("Error: " + e.getMessage());
                    return null;
                });

        while (!process.isDone()) {
            System.out.println("Main thread is free... doing other stuff :3");
            sleep(300);
        }

        executor.shutdown();
        System.out.println("----- ENDS ------");
    }


    public static class Order {
        private String name;
        private double price;

        public Order(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }
    }

    public static class OrderRepository {
        public static Optional<Order> findById(Integer id) {
            sleep(4000);
            System.out.println("Order found in DB");
            return (id == 1) ? Optional.of(new Order("RTX 5090", 2000.0)) : Optional.empty();
        }
    }

    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
