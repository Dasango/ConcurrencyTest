package com.uce.persistence.repositories.imple;


import com.uce.persistence.models.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManagerFactory;

import java.util.concurrent.ExecutorService;

@ApplicationScoped
public class UsuarioRepository extends BaseRepository<Usuario, Integer> {

    @Inject
    public UsuarioRepository(ExecutorService executorService, EntityManagerFactory entityManagerFactory) {
        super(executorService, entityManagerFactory, Usuario.class);
    }

}