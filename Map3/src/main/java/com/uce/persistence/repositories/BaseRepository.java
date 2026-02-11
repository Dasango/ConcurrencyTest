package com.uce.persistence.repositories;

import com.uce.persistence.repositories.inter.IBaseRepository;
import jakarta.persistence.EntityManagerFactory;

public class BaseRepository<CLASS, ID> implements IBaseRepository<CLASS, ID> {

    private EntityManagerFactory emf;
    private Class<CLASS> clazz;

    public BaseRepository(EntityManagerFactory emf, Class<CLASS> clazz) {
        this.emf = emf;
        this.clazz = clazz;
    }

    @Override
    public void delete(ID id){
        this.emf.runInTransaction(em ->{
            em.remove(
                    em.getReference(clazz, id)
            );
        });
    }

    @Override
    public CLASS save(CLASS obj){
        this.emf.runInTransaction(em -> em.persist(obj));
        return obj;
    }

    @Override
    public CLASS update(CLASS obj){
        this.emf.runInTransaction(em -> em.merge(obj));
        return obj;
    }

    @Override
    public CLASS findById(CLASS obj){
        return this.emf.callInTransaction(em -> em.find(clazz,obj));
    }

}
