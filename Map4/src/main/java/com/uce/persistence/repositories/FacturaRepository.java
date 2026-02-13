package com.uce.persistence.repositories;

import com.uce.persistence.Autorizacion;
import com.uce.persistence.Factura;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManagerFactory;

import java.util.concurrent.ExecutorService;

public class FacturaRepository extends BaseRepository<Factura,Integer> {

    @Inject
    public FacturaRepository(@Named("ProcesamientoLotes") ExecutorService executorService, EntityManagerFactory emf) {
        super(executorService, emf, Factura.class);
    }
}
