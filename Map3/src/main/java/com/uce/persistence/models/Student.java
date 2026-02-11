package com.uce.persistence.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "univ_gen")
    @SequenceGenerator(
            name = "univ_gen",
            sequenceName = "universidad_Seq",
            allocationSize = 10
    )
    private Integer id;
    @Column(length = 100, nullable = false)
    private String fullName;
    @Column(length = 100)
    private String email;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Column(name = "is_active")
    private Boolean isActive;
    private Integer version;
    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private StudentCard studentCard;
    @OneToMany(mappedBy = "student",fetch = FetchType.LAZY)
    @ToString.Exclude
    List<Enrollment> enrollments;
}
