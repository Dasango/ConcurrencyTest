package com.uce.services.concurrencia;

import com.uce.persistence.models.Project;
import com.uce.persistence.models.Task;
import com.uce.persistence.models.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManagerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;

@ApplicationScoped
public class LiteralUno {
    @Inject
    private EntityManagerFactory emf;
    @Inject
    private ExecutorService executorService;
    public void get(){

        var task1 = CompletableFuture.runAsync(
            new ConcurrenciaTask1<Usuario>("users.csv", this.emf, this::userMapper)
                , this.executorService);
        var task2 = CompletableFuture.runAsync(
                new ConcurrenciaTask1<Project>("users.csv", this.emf, this::projectMapper)
                , this.executorService);
        var task3 = CompletableFuture.runAsync(
                new ConcurrenciaTask1<Task>("users.csv", this.emf, this::taskMapper)
                , this.executorService);

        var allFinished = CompletableFuture.allOf(task1,task2,task3);

        //Block
        allFinished.join();
    }
    public Usuario userMapper(String [] args){
        return Usuario.builder()
                .name(args[0])
                .passwd(args[1])
                .build();
    }
    public Project projectMapper(String [] args){
        return Project.builder()
                .name(args[0])
                .version(Integer.valueOf(args[1]))
                .build();
    }
    public Task taskMapper(String [] args){

        var user= this.emf.callInTransaction(entityManager -> {
            return entityManager.find(Usuario.class, Integer.valueOf(args[0]));
        });
        var project = this.emf.callInTransaction(entityManager -> {
            return entityManager.find(Project.class, Integer.valueOf(args[1]));
        });
        return Task.builder()
                .user(user)
                .project(project)
                .build();
    }
}
