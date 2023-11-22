package com.example.militarymachineshangar.repository;

import com.example.militarymachineshangar.models.MilitaryMachineType;
import com.example.militarymachineshangar.models.jpa.Hangar;
import com.example.militarymachineshangar.models.jpa.Machine;
import com.example.militarymachineshangar.models.jpa.MachineModel;

public class TestUtils {

    public static Machine getTestTank() {
        return Machine.builder()
                .model(
                        MachineModel.builder()
                                .type(MilitaryMachineType.TANK)
                                .model("Leopard 2A4")
                                .price(20_000)
                                .build()
                )
                .hangar(
                        Hangar.builder()
                                .hangarName("Hangar-01")
                                .build()
                )
                .build();
    }

    public static Machine getTestCWH() {
        return Machine.builder()
                .model(
                        MachineModel.builder()
                                .type(MilitaryMachineType.COMBAT_WHEELED_VEHICLES)
                                .model("BTR-4")
                                .price(12_500)
                                .build()
                )
                .hangar(
                        Hangar.builder()
                                .hangarName("Hangar-02")
                                .build()
                )
                .build();
    }
}
