package com.example.hangar.service.impl;

import com.example.hangar.dto.MachinesListResponse;
import com.example.hangar.model.Machine;
import com.example.hangar.dto.MachineRequest;
import com.example.hangar.dto.MachineResponse;
import com.example.hangar.repository.MachineRepository;
import com.example.hangar.service.HangarService;
import com.example.hangar.utils.MyMapper;
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
                .map(MyMapper::mapToResponse)
                .map(body -> ResponseEntity.ok().body(body))

                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Override
    public ResponseEntity<MachinesListResponse> getAll() {
        List<MachineResponse> machineResponses = machineRepository.findAll().stream()
                .map(MyMapper::mapToResponse)
                .toList();

        MachinesListResponse body = new MachinesListResponse(machineResponses);

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

}
