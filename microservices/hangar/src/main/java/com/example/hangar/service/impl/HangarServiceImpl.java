package com.example.hangar.service.impl;

import com.example.hangar.model.Machine;
import com.example.hangar.dto.MachineRequest;
import com.example.hangar.dto.MachineResponse;
import com.example.hangar.repository.MachineRepository;
import com.example.hangar.service.HangarService;
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
    public ResponseEntity<Void> post(MachineRequest machineRequest) {
        Machine machine = Machine.builder()
                .type(machineRequest.getType())
                .model(machineRequest.getModel())
                .price(machineRequest.getPrice())
                .build();

        machineRepository.save(machine);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public ResponseEntity<MachineResponse> get(Long machineId) {

        return machineRepository.findById(machineId)
                .map(this::mapToResponse)
                .map(body -> ResponseEntity.ok().body(body))

                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

//        Optional<Machine> machineOptional = machineRepository.findById(machineId);
//
//        if (machineOptional.isEmpty()) {
//            return ResponseEntity
//                    .status(HttpStatus.NOT_FOUND)
//                    .build();
//        }
//
//        MachineResponse body = machineOptional
//                .map(this::mapToResponse)
//                .get();
//
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(body);
    }

    @Override
    public ResponseEntity<List<MachineResponse>> getAll() {
        List<MachineResponse> body = machineRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(body);
    }

    @Override
    public ResponseEntity<Void> deleteById(Long machineId) {
        machineRepository.deleteById(machineId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    private MachineResponse mapToResponse(Machine machine) {
        return MachineResponse.builder()
                .id(machine.getId())
                .type(machine.getType())
                .model(machine.getModel())
                .price(machine.getPrice())
                .build();
    }
}
