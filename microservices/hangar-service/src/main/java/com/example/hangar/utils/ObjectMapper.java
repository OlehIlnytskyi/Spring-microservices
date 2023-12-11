package com.example.hangar.utils;

import com.example.hangar.dto.MachineRequest;
import com.example.hangar.dto.MachineResponse;
import com.example.hangar.model.Machine;

public class ObjectMapper {

    public static MachineResponse mapToResponse(Machine machine) {
        return MachineResponse.builder()
                .id(machine.getId())
                .type(machine.getType())
                .model(machine.getModel())
                .price(machine.getPrice())
                .build();
    }

    public static Machine mapToBase(MachineRequest machineRequest) {
        return Machine.builder()
                .type(machineRequest.getType())
                .model(machineRequest.getModel())
                .price(machineRequest.getPrice())
                .build();
    }

}
