package com.example.hangar.controller;

import com.example.hangar.model.MachineRequest;
import com.example.hangar.model.MachineResponse;
import com.example.hangar.service.HangarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hangar")
public class HangarController {

    @Autowired
    private HangarService hangarService;

    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public void post(@RequestBody MachineRequest machineRequest) {
        hangarService.post(machineRequest);
    }

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.FOUND)
    public MachineResponse get(@RequestParam Long machineId) {
        return hangarService.get(machineId);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<MachineResponse> getAll() {
        return hangarService.getAll();
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@RequestParam Long machineId) {
        hangarService.deleteById(machineId);
    }
}
