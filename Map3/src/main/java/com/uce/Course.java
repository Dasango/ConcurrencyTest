package com.uce;

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
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "univ_gen")
    @SequenceGenerator(
            name = "univ_gen",
            sequenceName = "universidad_Seq",
            allocationSize = 10
    )
    private Integer id;
    @Column(length = 100)
    private String courseName;
    private String description;
    @Column(length = 10)
    private String code;
    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "courses")
    @ToString.Exclude
    private List<Enrollment> enrollments;
}
