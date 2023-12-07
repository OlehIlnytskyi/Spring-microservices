package com.example.hangar.controller;

import com.example.hangar.dto.MachineRequest;
import com.example.hangar.dto.MachineResponse;
import com.example.hangar.service.HangarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hangar")
public class HangarController {

    @Autowired
    private HangarService hangarService;

    @PostMapping("/post")
    public ResponseEntity<Void> post(@RequestBody MachineRequest machineRequest) {
        return hangarService.post(machineRequest);
    }

    @GetMapping("/get")
    public ResponseEntity<MachineResponse> get(@RequestParam Long machineId) {
        return hangarService.get(machineId);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<MachineResponse>> getAll() {
        return hangarService.getAll();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam Long machineId) {
        return hangarService.deleteById(machineId);
    }
}
