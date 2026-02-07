package com.uce.persistence.repositories.imple;

import com.uce.persistence.repositories.inter.IBaseRepository;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManagerFactory;
import lombok.Getter;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;

@Getter
public class BaseRepository<CLASS, ID>  implements IBaseRepository<CLASS, ID>  {

    private ExecutorService executorService;
    private EntityManagerFactory entityManagerFactory;
    private Class<CLASS> clazz;

    public BaseRepository(ExecutorService executorService,
                          EntityManagerFactory entityManagerFactory,
                          Class<CLASS> clazz) {
        this.executorService = executorService;
        this.entityManagerFactory = entityManagerFactory;
        this.clazz = clazz;
    }

    @Override
    public CompletableFuture<CLASS> save(CLASS obj) {
        return CompletableFuture.supplyAsync(()->{
                    this.entityManagerFactory
                            .runInTransaction(emf->{
                                emf.persist(obj);
                            });
                    return obj;
                }
                , this.executorService);
    }

    @Override
    public CompletableFuture<Void> delete(ID id, Runnable onOk, Function<Throwable,Void> onError) {
        return CompletableFuture.runAsync(()->{
                    this.entityManagerFactory
                            .runInTransaction(emf->{
                                emf.remove(
                                        emf.getReference(this.clazz, id)
                                );
                            });
                }
                , this.executorService)
                .thenRun(onOk)
                .exceptionally(onError);
    }

    @Override
    public CompletableFuture<Void> update(ID id, Runnable onOk, Function<Throwable,Void> onError) {
        return CompletableFuture.runAsync(()->{
                            this.entityManagerFactory
                                    .runInTransaction(emf->{
                                        emf.merge(
                                                emf.getReference(this.clazz, id)
                                        );
                                    });
                        }
                        , this.executorService)
                .thenRun(onOk)
                .exceptionally(onError);
    }

    @Override
    public CompletableFuture<CLASS> findById(ID id) {
        return CompletableFuture.supplyAsync(()->{
            return this.entityManagerFactory
                    .callInTransaction( em-> em.find(this.clazz, id));
        }, this.executorService);
    }

    @Override
    public CompletableFuture<List<CLASS>> findAll() {
        return CompletableFuture.supplyAsync(()->{
            return this.entityManagerFactory
                    .callInTransaction( em-> {

                        String jpql = "SELECT e FROM "+ this.clazz.getSimpleName()+" e";

                        return em.createQuery(jpql, this.clazz)
                                .getResultList();
                    });
        }, this.executorService);
    }
}
