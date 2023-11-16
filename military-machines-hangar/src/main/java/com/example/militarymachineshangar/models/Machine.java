package com.example.militarymachineshangar.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "military-vehicles")
public class Machine {

    @Id
    private long id;

    @Enumerated(EnumType.STRING)
    private MilitaryMachineType type;
    private String model;
    private LocalDate creationDate;
    private LocalDate expirationDat;
    private int count;
}
