package com.david.cpuHater;

import com.david.cpuHater.persistence.models.*;
import com.david.cpuHater.persistence.repositories.ClientRepository;
import com.david.cpuHater.persistence.repositories.FlightRepository;
import com.david.cpuHater.persistence.repositories.ReservationRepository;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataSeeder {

    private static final String[] NOMBRES = {"David", "Maria", "Juan", "Ana", "Carlos", "Sofia", "Luis", "Elena"};
    private static final String[] APELLIDOS = {"Perez", "Gomez", "Lopez", "Diaz", "Torres", "Ruiz", "Silva", "Mina"};
    private static final String[] TIPOS_VUELO = {"Turista", "Ejecutivo", "Primera Clase"};

    public static void main(String[] args) {
        Logger.getLogger("").setLevel(Level.SEVERE);

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()){

            var clientRepo = container.select(ClientRepository.class).get();
            var flightRepo = container.select(FlightRepository.class).get();
            var reservationRepo = container.select(ReservationRepository.class).get();

            System.out.println("Iniciando Data Seeding...");

            Random random = new Random();

            for (int i = 0; i < NOMBRES.length; i++) {
                Client cliente = new Client();
                cliente.setNombre(NOMBRES[i]);
                cliente.setApellido(APELLIDOS[i]);
                cliente.setCedula(100000 + i);
                cliente.setUsername(NOMBRES[i].toLowerCase() + i);
                cliente.setFechaNacimiento(LocalDateTime.now().minusYears(20 + random.nextInt(30)));
                cliente.setGender(i % 2 == 0 ? Gender.MALE : Gender.FEMALE);
                clientRepo.save(cliente);

                Flight flight = new Flight();
                flight.setNombre("Vuelo-" + (100 + i));
                flight.setDisponibilidad("DISPONIBLE");
                flight.setTipoVuelo(TIPOS_VUELO[random.nextInt(TIPOS_VUELO.length)]);

                flightRepo.save(flight);

                if (i < 5) {
                    Reservation reservation = new Reservation();
                    reservation.setCliente(cliente);
                    reservation.setFlight(flight);
                    reservation.setEstado(Status.CONFIRMED);

                    reservationRepo.save(reservation);

                    flight.setDisponibilidad("OCUPADO");
                    flightRepo.save(flight);
                }
            }

            System.out.println("Datos cargados exitosamente.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}