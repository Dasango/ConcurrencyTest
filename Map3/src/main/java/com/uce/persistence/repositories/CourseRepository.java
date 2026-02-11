package com.uce.persistence.repositories;

import com.uce.persistence.models.Course;
import jakarta.persistence.EntityManagerFactory;

public class CourseRepository extends BaseRepository<Course,Integer> {

    public CourseRepository(EntityManagerFactory emf) {
        super(emf, Course.class);
    }

}
