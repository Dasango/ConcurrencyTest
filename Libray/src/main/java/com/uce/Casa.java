package com.uce;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Casa {
    @Id
    Integer casa;
    @Column
    String algo;
}
