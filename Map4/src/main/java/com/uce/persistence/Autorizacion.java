package com.uce.persistence;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Autorizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secuencia_general")
    @SequenceGenerator(name = "secuencia_general",
            sequenceName = "au_id_seq",
            allocationSize = 1)
    private Integer id;
    @Column(length = 49)
    private String claveAcceso;
    @Column(length = 16)
    private String estado;
    @OneToOne(mappedBy = "autorizacion", cascade = CascadeType.ALL)
    private Factura factura;
}
