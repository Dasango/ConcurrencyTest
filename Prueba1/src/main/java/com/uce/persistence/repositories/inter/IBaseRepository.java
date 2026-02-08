package com.uce.persistence.repositories.inter;

import jakarta.persistence.EntityManagerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;

public interface IBaseRepository <CLASS, ID> {

    public CompletableFuture<CLASS> findById(ID id);

    public void remove(ID id, Function<Throwable, Void> onError, Runnable onOk);

    public void create(CLASS obj, Function<Throwable, Void> onError, Runnable onOk);

    public void update(CLASS obj, Function<Throwable, Void> onError, Runnable onOk);
}
