package com.uce.persistence.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "appusers")
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "user_gen")
    @TableGenerator(name= "user_gen", table = "claves_primarias", pkColumnValue = "Usuario")
    private Integer id;
    @Column(length = 32)
    private String passwd;
    @Column(length = 32)
    private String name;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Task> tasks;
    private LocalDateTime created;
    private Integer version;

    public static Usuario of(String[] it){
        return  null;
    }
}
