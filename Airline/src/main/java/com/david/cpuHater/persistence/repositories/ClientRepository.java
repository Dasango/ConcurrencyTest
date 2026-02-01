package com.david.cpuHater.persistence.repositories;

import com.david.cpuHater.persistence.models.Client;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.concurrent.ExecutorService;

@ApplicationScoped
public class ClientRepository extends BaseRepository<Client,Integer> {

    @Inject
    public ClientRepository(EntityManager entityManager, ExecutorService executorService) {
        super(entityManager, executorService, Client.class);
    }
}
