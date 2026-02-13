package com.uce.persistence.repositories;

import com.uce.persistence.Autorizacion;
import com.uce.persistence.Empresa;
import com.uce.persistence.Factura;
import com.uce.persistence.Producto;
import jakarta.persistence.EntityManagerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Function;

public class BaseRepository <T,ID> implements IBaseRepository<T, ID> {
    private ExecutorService executorService;
    private EntityManagerFactory emf;
    private Class<T> clazz;

    public BaseRepository(ExecutorService executorService, EntityManagerFactory emf, Class<T> clazz) {
        this.executorService = executorService;
        this.emf = emf;
        this.clazz = clazz;
    }

    @Override
    public CompletableFuture<T> create(T obj){
        return CompletableFuture.supplyAsync(()->{
            this.emf.runInTransaction(((em)->em.persist(obj)));
            return obj;
            }
        ,this.executorService);
    }
    @Override
    public CompletableFuture<Void> remove(ID id, Runnable onOk, Function<Throwable, Void> onError){
        return CompletableFuture.runAsync(()->{
                    this.emf.runInTransaction(((em)->em.remove(
                            em.getReference(clazz,id)
                    )));
                }
                ,this.executorService)
                .thenRun(onOk)
                .exceptionally(onError);
    }
    @Override
    public CompletableFuture<T> update(T obj){
        return CompletableFuture.supplyAsync(()->{
                    return this.emf.callInTransaction(((em)->em.merge(obj)));
                }
                ,this.executorService);
    }
    @Override
    public CompletableFuture<T> findById(ID id){
        return CompletableFuture.supplyAsync(()->{
                    return this.emf.callInTransaction(((em)->em.find(clazz,id)));
                }
                ,this.executorService);
    }
}
