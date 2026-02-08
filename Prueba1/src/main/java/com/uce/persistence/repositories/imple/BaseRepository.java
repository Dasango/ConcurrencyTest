package com.uce.persistence.repositories.imple;

import com.uce.persistence.repositories.inter.IBaseRepository;
import jakarta.persistence.EntityManagerFactory;
import lombok.Getter;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;

@Getter
public abstract class BaseRepository <CLASS, ID> implements IBaseRepository<CLASS, ID> {
    private EntityManagerFactory emf;
    private ExecutorService executorService;
    private Class<CLASS> clazz;

    public BaseRepository(EntityManagerFactory emf, ExecutorService executorService, Class<CLASS> clazz) {
        this.emf = emf;
        this.executorService = executorService;
        this.clazz = clazz;
    }

    public CompletableFuture<CLASS> findById(ID id){
        return CompletableFuture.supplyAsync(()->{
            return this.emf.callInTransaction((em)->{
                return em.find(this.clazz,id);
            });
        }, this.executorService);
    }
    public void remove(ID id, Function<Throwable, Void> onError, Runnable onOk){
        CompletableFuture.runAsync(()->{
            this.emf.runInTransaction((em)->{
                var obj = em.find(this.clazz,id);
                em.remove(obj);
            });
        }, this.executorService)
                .exceptionally(onError)
                .thenRun(onOk)
        ;
    }
    public void create(CLASS obj, Function<Throwable, Void> onError, Runnable onOk){
        CompletableFuture.runAsync(()->{
                    this.emf.runInTransaction((em)->{
                        em.persist(obj);
                    });
                }, this.executorService)
                .exceptionally(onError)
                .thenRun(onOk)
        ;
    }
    public void update(CLASS obj, Function<Throwable, Void> onError, Runnable onOk){
        CompletableFuture.runAsync(()->{
                    this.emf.runInTransaction((em)->{
                        em.merge(obj);
                    });
                }, this.executorService)
                .exceptionally(onError)
                .thenRun(onOk)
        ;
    }
}
