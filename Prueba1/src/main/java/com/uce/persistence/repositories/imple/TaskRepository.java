package com.uce.persistence.repositories.imple;

import com.uce.persistence.models.Project;
import com.uce.persistence.models.Task;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManagerFactory;

import java.util.concurrent.ExecutorService;

@ApplicationScoped
public class TaskRepository extends BaseRepository<Task,Integer> {

    @Inject
    public TaskRepository(EntityManagerFactory emf, ExecutorService executorService) {
        super(emf, executorService, Task.class);
    }
}
