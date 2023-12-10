package com.example.hangar.service;

import com.example.hangar.dto.MachineRequest;
import com.example.hangar.dto.MachineResponse;
import com.example.hangar.dto.MachinesListResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface HangarService {
    ResponseEntity<Void> post(MachineRequest machineRequest);

    ResponseEntity<MachineResponse> get(Long machineId);

    ResponseEntity<MachinesListResponse> getAll();

    ResponseEntity<Void> deleteById(Long machineId);
}
