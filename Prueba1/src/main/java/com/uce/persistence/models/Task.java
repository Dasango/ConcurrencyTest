package com.uce.persistence.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @TableGenerator(name = "gen_tasks", table = "claves_primarias", pkColumnValue = "tasks")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "gen_tasks")
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private Project project;
    private LocalDateTime created;
    @Column(length = 64)
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
}
