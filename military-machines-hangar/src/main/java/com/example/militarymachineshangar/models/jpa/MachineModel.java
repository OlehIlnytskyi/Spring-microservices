package com.example.militarymachineshangar.models.jpa;


import com.example.militarymachineshangar.models.MilitaryMachineType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "machine_model")
public class MachineModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_id")
    private long id;

    @Enumerated(EnumType.STRING)
    private MilitaryMachineType type;

    private String model;
    private int price;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "model")
    private Set<Machine> machines;
}
