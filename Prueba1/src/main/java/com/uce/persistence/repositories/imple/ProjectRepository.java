package com.uce.persistence.repositories.imple;

import com.uce.persistence.models.Project;
import com.uce.persistence.models.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManagerFactory;

import java.util.concurrent.ExecutorService;

@ApplicationScoped
public class ProjectRepository extends BaseRepository<Project,Integer> {
    @Inject
    public ProjectRepository(EntityManagerFactory emf, ExecutorService executorService) {
        super(emf, executorService, Project.class);
    }
}
