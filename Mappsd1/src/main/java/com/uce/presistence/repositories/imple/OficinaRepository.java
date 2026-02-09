package com.uce.presistence.repositories.imple;

import com.uce.Oficina;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManagerFactory;

import java.util.concurrent.ExecutorService;

@ApplicationScoped
public class OficinaRepository extends BaseRepository<Oficina,Integer> {

    @Inject
    public OficinaRepository(EntityManagerFactory entityManagerFactory, ExecutorService executor) {
        super(entityManagerFactory, executor, Oficina.class);
    }
}
