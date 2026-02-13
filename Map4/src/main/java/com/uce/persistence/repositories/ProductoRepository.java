package com.uce.persistence.repositories;

import com.uce.persistence.Autorizacion;
import com.uce.persistence.Producto;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManagerFactory;

import java.util.concurrent.ExecutorService;

public class ProductoRepository extends BaseRepository<Producto,Integer> {

    @Inject
    public ProductoRepository(@Named("ProcesamientoLotes") ExecutorService executorService, EntityManagerFactory emf) {
        super(executorService, emf, Producto.class);
    }
}
