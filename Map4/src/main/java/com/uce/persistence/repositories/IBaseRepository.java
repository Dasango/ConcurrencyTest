package com.uce.persistence.repositories;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public interface IBaseRepository<T, ID> {
    CompletableFuture<T> create(T obj);

    CompletableFuture<Void> remove(ID id, Runnable onOk, Function<Throwable, Void> onError);

    CompletableFuture<T> update(T obj);

    CompletableFuture<T> findById(ID id);
}
