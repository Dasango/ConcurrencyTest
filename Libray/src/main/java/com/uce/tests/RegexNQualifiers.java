package com.uce.tests;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@ApplicationScoped
public class RegexNQualifiers {

    public enum Tipo { ID, MENSAJE }

    @Qualifier
    @Retention(RUNTIME)
    @Target({FIELD, TYPE, METHOD, PARAMETER})
    public @interface Extractor {
        Tipo value() default Tipo.ID;
    }

    public interface LogStrategy {
        String extraer(String linea);
    }

    @ApplicationScoped @Extractor(Tipo.ID)
    public static class ExtractorId implements LogStrategy {
        @Override
        public String extraer(String linea) {
            Pattern p = Pattern.compile("ID:([\\d]+)");
            Matcher m = p.matcher(linea);
            if(m.find()) {
                return m.group(1);
            }
            return "";
        }
    }

    @ApplicationScoped @Extractor(Tipo.MENSAJE)
    public static class ExtractorMsg implements LogStrategy {
        @Override
        public String extraer(String linea) {
            Pattern p = Pattern.compile("MSG:([a-zA-Z_]+)");
            Matcher m = p.matcher(linea);
            if(m.find()) {
                return m.group(1);
            }
            return "";
        }
    }

    @ApplicationScoped
    public static class LogService {

        @Inject @Extractor(Tipo.ID)
        LogStrategy strategyId;
        @Inject @Extractor(Tipo.MENSAJE)
        LogStrategy strategyMsg;

        public void procesarLog(String linea) {
            System.out.println("Procesando linea: " + linea);
            System.out.println("ID Encontrado: " + strategyId.extraer(linea));
            System.out.println("Mensaje: " + strategyMsg.extraer(linea));
        }
    }

    public static void main(String[] args) {
        Logger.getLogger("").setLevel(Level.SEVERE);
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            LogService service = container.select(LogService.class).get();

            String logLine = "[INFO] ID:4592 -- MSG:Usuario_Logueado ID:45912 ";
            service.procesarLog(logLine);
        }
    }
}