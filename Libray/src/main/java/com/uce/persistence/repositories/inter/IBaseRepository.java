package com.uce.persistence.repositories.inter;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public interface IBaseRepository<CLASS, KEY> {

    public CompletableFuture<CLASS> save(CLASS obj);
    public CompletableFuture<Void> delete(KEY id, Runnable onOk, Function<Throwable,Void> onError);
    public CompletableFuture<Void> update(KEY id, Runnable onOk, Function<Throwable,Void> onError);
    public CompletableFuture<CLASS> findById(KEY id);
    public CompletableFuture<List<CLASS>> findAll();

}
