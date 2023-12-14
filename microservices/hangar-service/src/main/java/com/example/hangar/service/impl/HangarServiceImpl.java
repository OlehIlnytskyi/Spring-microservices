package com.example.hangar.service.impl;

import com.example.hangar.dto.MachineResponseListWrapper;
import com.example.hangar.model.Machine;
import com.example.hangar.dto.MachineRequest;
import com.example.hangar.dto.MachineResponse;
import com.example.hangar.repository.MachineRepository;
import com.example.hangar.service.HangarService;
import com.example.hangar.utils.MachineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HangarServiceImpl implements HangarService {

    @Autowired
    private MachineRepository machineRepository;

    @Override
    public ResponseEntity<Void> addMachine(List<MachineRequest> machineArrayRequest) {
        List<Machine> machineList = machineArrayRequest.stream()
                        .map(MachineMapper::mapToBase)
                                .toList();

        machineRepository.saveAll(machineList);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public ResponseEntity<MachineResponse> getMachineById(Long machineId) {
        return machineRepository.findById(machineId)
                .map(MachineMapper::mapToResponse)
                .map(body -> ResponseEntity.ok().body(body))

                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Override
    public ResponseEntity<MachineResponseListWrapper> getAllMachinesInWrapper() {
        MachineResponseListWrapper body = MachineResponseListWrapper.of(getAllMachines());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(body);
    }

    @Override
    public List<MachineResponse> getAllMachines() {
        return machineRepository.findAll().stream()
                .map(MachineMapper::mapToResponse)
                .toList();
    }

    @Override
    public ResponseEntity<Void> deleteMachineById(Long machineId) {
        machineRepository.deleteById(machineId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

}
