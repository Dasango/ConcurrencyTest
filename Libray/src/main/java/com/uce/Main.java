package com.uce;

import com.uce.persistence.models.Project;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        //Logger.getLogger("").setLevel(Level.SEVERE);
        try(SeContainer container = SeContainerInitializer.newInstance().initialize()){

            jakarta.persistence.Persistence.createEntityManagerFactory("PU")
                    .createEntityManager().find(Project.class,1);
            System.out.println("Compila");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}