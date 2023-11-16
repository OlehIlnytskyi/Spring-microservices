package com.example.clientinventory.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Machine {

    private long id;
    private MilitaryMachineType type;
    private String model;
    private LocalDate creationDate;
    private LocalDate expirationDate;
    private int count;
}
