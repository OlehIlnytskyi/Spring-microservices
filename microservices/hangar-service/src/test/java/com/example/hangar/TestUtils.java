package com.example.hangar;

import com.example.hangar.dto.MachineRequest;
import com.example.hangar.dto.MachineResponse;
import com.example.hangar.model.Machine;
import com.example.hangar.model.MachineType;

import java.math.BigDecimal;

public class TestUtils {

    public static MachineRequest getTestMachineRequest() {
        return MachineRequest.builder()
                .type(MachineType.TANK)
                .model("Leopard-2A4")
                .price(BigDecimal.valueOf(25_000))
                .build();
    }

    public static MachineResponse getTestMachineResponse() {
        return MachineResponse.builder()
                .id(1L)
                .type(MachineType.CWV)
                .model("BTR-4")
                .price(BigDecimal.valueOf(7_500))
                .build();
    }

    public static Machine getTestMachineA() {
        return Machine.builder()
                .type(MachineType.CWV)
                .model("BTR-4")
                .price(BigDecimal.valueOf(7_500))
                .build();
    }

    public static Machine getTestMachineB() {
        return Machine.builder()
                .type(MachineType.TANK)
                .model("Leopard-2A4")
                .price(BigDecimal.valueOf(15_000))
                .build();
    }
}
