package com.example.hangar.service;

import com.example.hangar.model.MachineRequest;
import com.example.hangar.model.MachineResponse;

import java.util.List;

public interface HangarService {
    void post(MachineRequest machineRequest);

    MachineResponse get(Long machineId);

    List<MachineResponse> getAll();

    void deleteById(Long machineId);
}
