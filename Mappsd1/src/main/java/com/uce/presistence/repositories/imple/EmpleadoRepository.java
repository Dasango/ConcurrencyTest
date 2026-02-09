package com.uce.presistence.repositories.imple;

import com.uce.Empleado;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManagerFactory;

import java.util.concurrent.ExecutorService;

@ApplicationScoped
public class EmpleadoRepository extends BaseRepository<Empleado,Integer> {

    @Inject
    public EmpleadoRepository(EntityManagerFactory entityManagerFactory, ExecutorService executor) {
        super(entityManagerFactory, executor, Empleado.class);
    }
}
