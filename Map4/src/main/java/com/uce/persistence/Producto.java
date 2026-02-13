package com.uce.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secuencia_general")
    @SequenceGenerator(name = "secuencia_general",
            sequenceName = "pr_id_seq",
            allocationSize = 1)
    private Integer id;
    @Column(length = 32)
    private String codigo;
    @Column(length = 128)
    private String nombre;
    private BigDecimal precio;
    @ManyToMany(mappedBy = "productos", fetch = FetchType.LAZY)
    private Set<Factura> facturas;
}
