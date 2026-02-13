package com.uce.persistence.repositories;

import com.uce.persistence.Autorizacion;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManagerFactory;

import java.util.concurrent.ExecutorService;

public class AutorizacionRepository extends BaseRepository<Autorizacion,Integer> {

    @Inject
    public AutorizacionRepository(@Named("ProcesamientoLotes") ExecutorService executorService, EntityManagerFactory emf) {
        super(executorService, emf, Autorizacion.class);
    }
}
