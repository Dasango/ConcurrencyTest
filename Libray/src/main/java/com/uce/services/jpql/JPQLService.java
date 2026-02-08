package com.uce.services.jpql;

import com.uce.services.dto.LiteralDosDTO;
import com.uce.services.dto.LiteralUnoDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class JPQLService implements IJPQLService {
    private EntityManagerFactory emf;
    @Inject
    public JPQLService(EntityManagerFactory emf) {
        this.emf = emf;
    }
    @Override
    public List<LiteralUnoDTO> getLiteralUno(){
        String jpql = "SELECT " +
                " u.name," +
                " Count(t)" +
                " FROM Usuario u" +
                " JOIN u.tasks t" +
                " WHERE t.complete IS NOT NULL" +
                " GROUP BY u.name" +
                " HAVING Count(t) > 5";
        return this.emf.callInTransaction((em)->{
           return em.createQuery(jpql, LiteralUnoDTO.class)
                   .getResultList();
        });
    }

    @Override
    public List<LiteralDosDTO.TaskSummary> getLiteralDos(){
        String jpql = "SELECT " +
                " t.title, " +
                " p.name, " +
                " u.name" +
                " FROM Usuario u" +
                " JOIN u.tasks t" +
                " JOIN u.project p" +
                " WHERE MONTH(t.created) >= :ultimoMes";
        return this.emf.callInTransaction((em)->{
            return em.createQuery(jpql, LiteralDosDTO.TaskSummary.class)
                    .setParameter("ultimoMes", LocalDateTime.now().getMonth())
                    .getResultList();
        });
    }
}
