package com.example.hangar.service;

import com.example.hangar.dto.MachineRequest;
import com.example.hangar.dto.MachineResponse;
import com.example.hangar.dto.MachineResponseListWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface HangarService {
    ResponseEntity<Void> addMachine(List<MachineRequest> machineArrayRequest);

    ResponseEntity<MachineResponse> getMachineById(Long machineId);

    ResponseEntity<MachineResponseListWrapper> getAllMachinesInWrapper();

    List<MachineResponse> getAllMachines();

    ResponseEntity<Void> deleteMachineById(Long machineId);
}
