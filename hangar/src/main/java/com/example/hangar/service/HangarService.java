package com.example.hangar.service;

import com.example.hangar.dto.MachineRequest;
import com.example.hangar.dto.MachineResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface HangarService {
    ResponseEntity<Void> post(MachineRequest machineRequest);

    ResponseEntity<MachineResponse> get(Long machineId);

    ResponseEntity<List<MachineResponse>> getAll();

    ResponseEntity<Void> deleteById(Long machineId);
}
