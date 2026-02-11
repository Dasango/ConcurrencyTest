package com.uce.persistence.repositories;

import com.uce.persistence.models.Enrollment;
import com.uce.persistence.models.StudentCard;
import jakarta.persistence.EntityManagerFactory;

public class StudentCardRepository extends BaseRepository<StudentCard,Integer> {

    public StudentCardRepository(EntityManagerFactory emf) {
        super(emf, StudentCard.class);
    }

}