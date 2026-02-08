package com.uce.persistence.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "project_gen")
    @TableGenerator(name= "project_gen", table = "claves_primarias", pkColumnValue = "Project")
    private Integer id;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private List<Task> tasks;

    private LocalDateTime created;
    @Column(length = 32)
    private String name;

    private Integer version;


    public static Project of(String[] it){
        return  null;
    }
}
