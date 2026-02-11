package com.uce;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class StudentCard {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "univ_gen")
    @SequenceGenerator(
            name = "univ_gen",
            sequenceName = "universidad_Seq",
            allocationSize = 10
    )
    private Integer id;
    @Column(length = 20)
    private String cardNumber;
    private LocalDateTime issueDate;
    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    @ToString.Exclude
    private Student student;
}
