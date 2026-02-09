package com.uce.presistence.repositories.inter;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public interface IBaseRepository<CLASS, ID> {
    CompletableFuture<Void> create(CLASS obj, Runnable onOk, Function<Throwable, Void> onError);

    CompletableFuture<Void> delete(CLASS obj, Runnable onOk, Function<Throwable, Void> onError);

    CompletableFuture<Void> update(CLASS obj, Runnable onOk, Function<Throwable, Void> onError);

    CompletableFuture<CLASS> findById(ID id);
}
