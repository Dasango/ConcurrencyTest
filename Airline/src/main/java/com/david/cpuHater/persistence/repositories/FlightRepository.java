package com.david.cpuHater.persistence.repositories;

import com.david.cpuHater.persistence.models.Flight;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.concurrent.ExecutorService;

@ApplicationScoped
public class FlightRepository extends BaseRepository<Flight,Integer> {

    @Inject
    public FlightRepository(EntityManager entityManager, ExecutorService executorService) {
        super(entityManager, executorService, Flight.class);
    }
}
