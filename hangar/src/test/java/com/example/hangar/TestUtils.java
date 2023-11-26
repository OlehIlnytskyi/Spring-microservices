package com.example.hangar;

import com.example.hangar.model.Machine;
import com.example.hangar.model.MachineType;

import java.math.BigDecimal;

public class TestUtils {

    public static Machine getTestMachine_Tank() {
        return Machine.builder()
                .type(MachineType.Tank)
                .model("Leopard-2A4")
                .price(BigDecimal.valueOf(25_000))
                .build();
    }
}
