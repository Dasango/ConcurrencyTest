package com.david.cpuHater.persistence.repositories;

import com.david.cpuHater.persistence.notification.MessageObserver;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

@Getter
public abstract class BaseRepository<T, ID> {

    private EntityManager entityManager;
    private ExecutorService executorService;
    private Class<T> entityClass;
    private Event<MessageObserver> observerEvent;


    public BaseRepository(EntityManager entityManager, ExecutorService executorService, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.executorService = executorService;
        this.entityClass = entityClass;
    }

    private void runInTransaction(Consumer<EntityManager> action) {
        EntityManager em = getEntityManager();
        var transaction = em.getTransaction();
        try {
            transaction.begin();
            action.accept(em);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }

    public CompletableFuture<T> save(T obj) {
        return CompletableFuture.supplyAsync(() -> {
            runInTransaction(em -> em.persist(obj));
            return obj;
        }, getExecutorService());
    }

    public CompletableFuture<Optional<T>> findById(ID id) {
        return CompletableFuture.supplyAsync(() ->
                        Optional.ofNullable(getEntityManager().find(getEntityClass(), id)),
                getExecutorService()
        );
    }

    public CompletableFuture<List<T>> findAll(){
        return CompletableFuture.supplyAsync(()->{
            return this.entityManager
                    .createQuery("SELECT * FROM "+ getEntityClass().getSimpleName() + " e", getEntityClass())
                    .getResultList();
        }, getExecutorService());
    }

    public CompletableFuture<Void> remove(T obj) {
        return CompletableFuture.runAsync(() -> {
                    runInTransaction(em -> {
                        T merged = em.contains(obj) ? obj : em.merge(obj);
                        em.remove(merged);
                    });
                }, getExecutorService())
                .thenRun(() -> getObserverEvent().fire(new MessageObserver("Eliminado con éxito")))
                .exceptionally(ex -> {
                    getObserverEvent().fire(new MessageObserver("Error al eliminar: " + ex.getMessage()));
                    return null;
                });
    }
    public CompletableFuture<T> update(T obj){
        return CompletableFuture.supplyAsync(()->{
            runInTransaction((em) -> {
                em.merge(obj);
            });
            return obj;
        }, getExecutorService());
    }

}