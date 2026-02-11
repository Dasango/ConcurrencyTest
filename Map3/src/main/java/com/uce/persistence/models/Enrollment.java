package com.uce.persistence.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "univ_gen")
    @SequenceGenerator(
            name = "univ_gen",
            sequenceName = "universidad_Seq",
            allocationSize = 10
    )
    private Integer id;
    @Column(length = 10)
    private String semester;
    @Column(precision = 5, scale = 2)
    private BigDecimal totalCredits;
    @Column(name = "registered_at")
    private LocalDateTime registeredAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    @ToString.Exclude
    private Student student;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "inscripcion_cursos",
            joinColumns = @JoinColumn(name = "enrollment_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    @ToString.Exclude
    private List<Course> courses;

}
