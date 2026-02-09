package com.uce;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_VEHICULO")
    @SequenceGenerator(name = "GEN_VEHICULO", allocationSize = 30)
    private Integer codVehiculo;
    private String descripcion;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "codVehiculo" )
    private List<Reserva> reservas;
}
