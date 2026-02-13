package com.uce.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secuencia_general")
    @SequenceGenerator(name = "secuencia_general",
            sequenceName = "f_id_seq",
            allocationSize = 1)
    private Integer id;
    @Column(length = 15)
    private String numero;
    private LocalDateTime fechaEmision;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "autorizacion_id")
    private Autorizacion autorizacion;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "factura_producto",
            joinColumns = @JoinColumn(name = "factura"),
            inverseJoinColumns = @JoinColumn(name = "producto")
    )
    private Set<Producto> productos;
}
