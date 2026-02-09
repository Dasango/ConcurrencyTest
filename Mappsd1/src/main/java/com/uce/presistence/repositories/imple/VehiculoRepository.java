package com.uce.presistence.repositories.imple;

import com.uce.Reserva;
import com.uce.Vehiculo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManagerFactory;

import java.util.concurrent.ExecutorService;

public class VehiculoRepository extends BaseRepository<Vehiculo,Integer> {

    @Inject
    public VehiculoRepository(EntityManagerFactory entityManagerFactory, ExecutorService executor) {
        super(entityManagerFactory, executor, Vehiculo.class);
    }
}
