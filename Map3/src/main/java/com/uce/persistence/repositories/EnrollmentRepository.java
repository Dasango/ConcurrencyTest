package com.uce.persistence.repositories;

import com.uce.persistence.models.Course;
import com.uce.persistence.models.Enrollment;
import jakarta.persistence.EntityManagerFactory;

public class EnrollmentRepository extends BaseRepository<Enrollment,Integer> {

    public EnrollmentRepository(EntityManagerFactory emf) {
        super(emf, Enrollment.class);
    }

}