package com.uce.persistence.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "task_gen")
    @TableGenerator(name= "task_gen", table = "claves_primarias", pkColumnValue = "Task")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private Project project;

    private LocalDateTime created;

    @Column(length = 64, nullable = false)
    private String title;

    private LocalDateTime complete;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Usuario user;

    @Column(length = 128)
    private String description;

    private Integer version;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    public static Task of(String[] it){
        return  null;
    }
}