package com.uce.persistence;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secuencia_general")
    @SequenceGenerator(name = "secuencia_general",
            sequenceName = "em_id_seq",
            allocationSize = 1)
    private Integer id;
    @Column(length = 13)
    private String ruc;
    @Column(length = 128)
    private String razonSocial;
    private Integer version;
    @OneToMany(mappedBy = "empresa",fetch = FetchType.LAZY)
    private Factura factura;
}
