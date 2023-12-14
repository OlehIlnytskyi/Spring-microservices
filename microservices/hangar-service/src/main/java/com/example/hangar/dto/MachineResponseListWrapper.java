package com.example.hangar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MachineResponseListWrapper {
    private List<MachineResponse> machineResponses;

    public static MachineResponseListWrapper of(List<MachineResponse> machineResponseList) {
        return new MachineResponseListWrapper(machineResponseList);
    }
}
