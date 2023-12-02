package com.example.hangar.service.impl;

import com.example.hangar.model.Machine;
import com.example.hangar.dto.MachineRequest;
import com.example.hangar.dto.MachineResponse;
import com.example.hangar.repository.MachineRepository;
import com.example.hangar.service.HangarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HangarServiceImpl implements HangarService {

    @Autowired
    private MachineRepository machineRepository;

    @Override
    public void post(MachineRequest machineRequest) {
        Machine machine = Machine.builder()
                .type(machineRequest.getType())
                .model(machineRequest.getModel())
                .price(machineRequest.getPrice())
                .build();

        machineRepository.save(machine);
    }

    @Override
    public MachineResponse get(Long machineId) {
        Machine machine = machineRepository.findById(machineId)
                .orElseThrow(() -> new IllegalArgumentException("Machine with id " + machineId + " is missing in the Hangar."));

        return MachineResponse.builder()
                .id(machine.getId())
                .type(machine.getType())
                .model(machine.getModel())
                .price(machine.getPrice())
                .build();
    }

    @Override
    public List<MachineResponse> getAll() {
        return machineRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deleteById(Long machineId) {
        machineRepository.deleteById(machineId);
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
