package com.example.militarymachineshangar.models.jpa;


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
@Table(name = "hangar")
public class Hangar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hangar_id")
    private long id;

    private String hangarName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hangar")
    private Set<Machine> machines;
}
