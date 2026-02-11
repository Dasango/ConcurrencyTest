package com.uce.persistence.repositories;

import com.uce.persistence.models.Student;
import com.uce.persistence.models.StudentCard;
import jakarta.persistence.EntityManagerFactory;

public class StudentRepository extends BaseRepository<Student,Integer> {

    public StudentRepository(EntityManagerFactory emf) {
        super(emf, Student.class);
    }

}