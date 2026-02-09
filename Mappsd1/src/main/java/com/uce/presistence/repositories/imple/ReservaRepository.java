package com.uce.presistence.repositories.imple;

import com.uce.Reserva;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManagerFactory;

import java.util.concurrent.ExecutorService;

@ApplicationScoped
public class ReservaRepository extends BaseRepository<Reserva,Integer> {

    @Inject
    public ReservaRepository(EntityManagerFactory entityManagerFactory, ExecutorService executor) {
        super(entityManagerFactory, executor, Reserva.class);
    }
}
