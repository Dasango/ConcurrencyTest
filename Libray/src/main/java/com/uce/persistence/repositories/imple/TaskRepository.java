package com.uce.persistence.repositories.imple;


import com.uce.persistence.models.Task;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManagerFactory;

import java.util.concurrent.ExecutorService;

public class TaskRepository extends BaseRepository<Task, Integer> {

    @Inject
    public TaskRepository(ExecutorService executorService, EntityManagerFactory entityManagerFactory) {
        super(executorService, entityManagerFactory, Task.class);
    }
}
