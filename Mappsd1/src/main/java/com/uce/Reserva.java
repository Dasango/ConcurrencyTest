package com.uce;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_RESERVA")
    @SequenceGenerator(name = "GEN_RESERVA", allocationSize = 30)
    private Integer codReserva;
    private LocalDateTime fecha;
    private String destino;
    private BigDecimal kilometros;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "codVehiculo")
    private Vehiculo codVehiculo;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;
}
