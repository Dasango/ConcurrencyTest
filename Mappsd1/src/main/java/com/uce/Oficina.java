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
public class Oficina {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_OFICINA")
    @SequenceGenerator(name = "GEN_OFICINA", allocationSize = 30)
    private Integer codOficina;
    private String dirrecion;
    private String localidad;
    @Enumerated(EnumType.STRING)
    private Provincia provincia;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "codOficina" )
    private List<Empleado> empleados;

}
