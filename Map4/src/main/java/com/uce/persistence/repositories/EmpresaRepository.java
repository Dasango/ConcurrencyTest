package com.uce.persistence.repositories;

import com.uce.persistence.Autorizacion;
import com.uce.persistence.Empresa;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManagerFactory;

import java.util.concurrent.ExecutorService;

public class EmpresaRepository extends BaseRepository<Empresa,Integer> {

    @Inject
    public EmpresaRepository(@Named("ProcesamientoLotes") ExecutorService executorService, EntityManagerFactory emf) {
        super(executorService, emf, Empresa.class);
    }
}
