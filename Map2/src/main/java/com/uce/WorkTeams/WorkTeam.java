package com.uce.WorkTeams;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class WorkTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "control_team")
    @TableGenerator(name = "control_team", table = "control_claves", pkColumnValue = "workteam")
    private Integer id;

    @Column(length = 64)
    private String teamName;

    private Integer version;

    @ManyToMany(mappedBy = "workTeams", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Employee> employees;
}
