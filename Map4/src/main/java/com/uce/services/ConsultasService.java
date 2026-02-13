package com.uce.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

@ApplicationScoped
public class ConsultasService {

    private EntityManagerFactory emf;

    public List<unoDTO> uno(){
        String jpql="SELECT " +
                " e.razonSocial," +
                " COUNT(f)" +
                " FROM Empresa e" +
                " JOIN e.facturas f" +
                " WHERE f.estado == 'AUTORIZADO'" +
                " GROUP BY  e.razonSocial" +
                " HAVING COUNT(f) >10" +
                " ORDER BY COUNT(f) DESC";

        return this.emf.callInTransaction(
                        em ->{
                            return em.createQuery(jpql, unoDTO.class)
                                    .getResultList();
                        }
                );
    }
    public record unoDTO(String r, Integer count){

    }
}
