package com.example.militarymachineshangar.controller;

import com.example.militarymachineshangar.models.Catalog;
import com.example.militarymachineshangar.models.Machine;
import com.example.militarymachineshangar.models.MilitaryMachineType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/hangar")
public class HangarController {

    private Catalog<Machine> militaryMachinesCatalog;

    {
        militaryMachinesCatalog = new Catalog<>(Map.of(
                new Machine(1, MilitaryMachineType.COMBAT_WHEELED_VEHICLES, "BMP-2", LocalDate.of(1980, 4, 12), LocalDate.of(2025, 4, 12)), 200,
                new Machine(2, MilitaryMachineType.COMBAT_WHEELED_VEHICLES, "BTR-4", LocalDate.of(2014, 2, 23), LocalDate.of(2049, 2, 23)), 125,
                new Machine(3, MilitaryMachineType.TANK, "Leopard 2A4", LocalDate.of(1979, 12, 25), LocalDate.of(2035, 12, 25)), 50,
                new Machine(4, MilitaryMachineType.TANK, "T-64BM", LocalDate.of(1967, 1, 2), LocalDate.of(2010, 1, 2)), 75
        ));
    }

    @GetMapping("/getAllMachines")
    public Catalog<Machine> getAllMachines() {
        return militaryMachinesCatalog;
    }
}
