package com.uce.services.JPQL;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManagerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutorService;

@ApplicationScoped
public class LiteralUnoService {
    @Inject
    private EntityManagerFactory emf;
    @Inject
    private ExecutorService executorService;


    public List<DTO> get(){
        String jpql = " SELECT u.name, COUNT(t)" +
                " FROM Usuario u" +
                " JOIN u.tasks t" +
                " WHERE t.complete IS NOT NULL" +
                " GROUP BY u.name" +
                " HAVING COUNT(t) >5";
        return this.emf.callInTransaction(em ->{
            return em.createQuery(jpql, DTO.class)
                    .getResultList();
        });
    }
}
