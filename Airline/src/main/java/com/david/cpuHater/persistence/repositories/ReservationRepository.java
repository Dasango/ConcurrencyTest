package com.david.cpuHater.persistence.repositories;

import com.david.cpuHater.persistence.models.Reservation;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.concurrent.ExecutorService;

@ApplicationScoped
public class ReservationRepository extends BaseRepository<Reservation,Integer>{

    @Inject
    public ReservationRepository(EntityManager entityManager, ExecutorService executorService) {
        super(entityManager, executorService, Reservation.class);
    }

}
