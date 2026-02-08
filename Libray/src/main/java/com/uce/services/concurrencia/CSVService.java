package com.uce.services.concurrencia;

import com.uce.persistence.models.Project;
import com.uce.persistence.models.Task;
import com.uce.persistence.models.Usuario;
import com.uce.services.concurrencia.tasks.ImportCSVTask;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManagerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@ApplicationScoped
public class CSVService {

    @Inject
    private EntityManagerFactory emf;
    @Inject
    private ExecutorService executorService;

    private void importar(){
        var users = CompletableFuture.runAsync(
                new ImportCSVTask<Usuario>(this.emf, Usuario::of, "users.csv")
        , this.executorService);
        var projects = CompletableFuture.runAsync(
                new ImportCSVTask<Project>(this.emf, Project::of, "projects.csv")
                , this.executorService);
        var tasks = CompletableFuture.runAsync(
                new ImportCSVTask<Task>(this.emf, Task::of, "tasks.csv")
                , this.executorService);
        CompletableFuture.allOf(users,projects,tasks).join();
    }
}
