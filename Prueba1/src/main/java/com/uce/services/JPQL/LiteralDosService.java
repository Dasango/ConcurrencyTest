package com.uce.services.JPQL;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManagerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;

@ApplicationScoped
public class LiteralDosService {
    @Inject
    private EntityManagerFactory emf;
    @Inject
    private ExecutorService executorService;

    public List<TaskSummary> get(){
        String jpql = " SELECT t.title, " +
                " p.name," +
                " u.name" +
                " FROM Usuario u" +
                " JOIN u.tasks t" +
                " JOIN t.project p" +
                " WHERE MONTH(t.created) >= :currentMonth";
        return this.emf.callInTransaction(em ->{
            return em.createQuery(jpql, TaskSummary.class)
                    .setParameter("currentMonth", LocalDateTime.now().getMonth())
                    .getResultList();
        });
    }
}
