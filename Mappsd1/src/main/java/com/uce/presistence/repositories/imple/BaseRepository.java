package com.uce.presistence.repositories.imple;

import com.uce.presistence.repositories.inter.IBaseRepository;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManagerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;

public class BaseRepository<CLASS, ID> implements IBaseRepository<CLASS, ID> {

    private EntityManagerFactory entityManagerFactory;

    private ExecutorService executor;

    private Class<CLASS> clazz;

    public BaseRepository(EntityManagerFactory entityManagerFactory, ExecutorService executor, Class<CLASS> clazz) {
        this.entityManagerFactory = entityManagerFactory;
        this.executor = executor;
        this.clazz = clazz;
    }

    @Override
    public CompletableFuture<Void> create(CLASS obj, Runnable onOk, Function<Throwable, Void> onError){
        return CompletableFuture.runAsync(()->{
            this.entityManagerFactory
                    .runInTransaction( em ->{
                        em.persist(obj);
                            }
                    );
        }, this.executor)
                .thenRun(onOk)
                .exceptionally(onError);
    }
    @Override
    public CompletableFuture<Void> delete(CLASS obj, Runnable onOk, Function<Throwable, Void> onError){
        return CompletableFuture.runAsync(()->{
            this.entityManagerFactory
                    .runInTransaction( em ->{
                                em.remove(obj);
                            }
                    );
                }, this.executor)
                .thenRun(onOk)
                .exceptionally(onError);
    }
    @Override
    public CompletableFuture<Void> update(CLASS obj, Runnable onOk, Function<Throwable, Void> onError){
        return CompletableFuture.runAsync(()->{
            this.entityManagerFactory
                    .runInTransaction( em ->{
                                em.merge(obj);
                            }
                    );
                }, this.executor)
                .thenRun(onOk)
                .exceptionally(onError);
    }
    @Override
    public CompletableFuture<CLASS> findById(ID id){
        return CompletableFuture.supplyAsync(()->{
            return this.entityManagerFactory
                    .callInTransaction( em ->{
                                return em.find(this.clazz, id);
                            }
                    );
        }, this.executor);
    }

}
