package com.uce.WorkTeams;

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
@ToString
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "control_emp")
    @TableGenerator(name = "control_emp", table = "control_claves", pkColumnValue = "employee")
    private Integer id;

    @Column(length = 32)
    private String username;

    private LocalDateTime createdAt;

    @Version
    private Integer version;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "access_profile_id")
    @ToString.Exclude
    private AccessProfile accessProfile;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "employee_teams",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<WorkTeam> workTeams;
}