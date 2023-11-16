package com.example.militarymachineshangar.controller;

import com.example.militarymachineshangar.models.Catalog;
import com.example.militarymachineshangar.models.Machine;
import com.example.militarymachineshangar.models.MilitaryMachineType;
import com.example.militarymachineshangar.service.HangarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/hangar")
public class HangarController {

    @Autowired
    private HangarService hangarService;

    @GetMapping("/insertData")
    public String insertData() {
        return hangarService.insertData();
    }

    @GetMapping("/getAllMachines")
    public Catalog<Machine> getAllMachines() {
        return hangarService.getAllMachines();
    }

    @GetMapping("/getMachine")
    public Optional<Machine> getMachine(@RequestParam long id){
        return hangarService.getMachine(id);
    }

    @PostMapping("/addMachine")
    public void addMachine(@RequestBody Machine machine) {
        hangarService.addMachine(machine);
    }

    @PutMapping("/editMachine")
    public void editMachine(@RequestBody Machine machine) {
        hangarService.editMachine(machine);
    }

    @DeleteMapping("removeMachine")
    public void removeMachine(@RequestParam long id, @RequestParam int count) {
        hangarService.removeMachine(id, count);
    }
}
