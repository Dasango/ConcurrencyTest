package com.uce.WorkTeams;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AccessProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "control_ap")
    @TableGenerator(name = "control_ap", table = "control_claves", pkColumnValue = "accessprofile")
    private Integer id;

    @Column(length = 32)
    private String profileName;

    @Column(length = 128)
    private String biography;

    private Integer version;

    @OneToOne(mappedBy = "accessProfile", fetch = FetchType.LAZY) // Bi-direccional y Lado "One" -> LAZY
    private Employee employee;
}
