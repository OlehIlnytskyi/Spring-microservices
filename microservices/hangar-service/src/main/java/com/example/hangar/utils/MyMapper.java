package com.example.hangar.utils;

import com.example.hangar.dto.MachineResponse;
import com.example.hangar.model.Machine;

public class MyMapper {

    public static MachineResponse mapToResponse(Machine machine) {
        return MachineResponse.builder()
                .id(machine.getId())
                .type(machine.getType())
                .model(machine.getModel())
                .price(machine.getPrice())
                .build();
    }

}
