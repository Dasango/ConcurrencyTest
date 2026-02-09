package com.uce;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_EMPLEADO")
    @SequenceGenerator(name = "GEN_EMPLEADO", allocationSize = 30)
    private Integer codEmpleado;
    private String nombre;
    private String apellidos;
    private BigDecimal salario;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "codOficina")
    private Oficina codOficina;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empleado")
    private List<Reserva> reservas;
}
