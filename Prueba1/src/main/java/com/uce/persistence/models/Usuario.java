package com.uce.persistence.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    @Id
    @TableGenerator(name = "claves_primarias", table = "claves_primarias", pkColumnValue = "project")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "claves_primarias")
    private Integer id;
    @Column(length = 32)
    private String passwd;
    @Column(length = 32)
    private String name;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Task> tasks;
    private LocalDateTime created;
    private Integer version;
}
