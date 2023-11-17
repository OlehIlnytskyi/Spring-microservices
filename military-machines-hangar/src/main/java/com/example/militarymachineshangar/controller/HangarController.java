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

    @GetMapping("/initValues")
    public String initValues() {
        return hangarService.initValues();
    }

    @GetMapping("/getAll")
    public Catalog<Machine> getAll() {
        return hangarService.getAll();
    }

    @GetMapping("/get")
    public Optional<Machine> get(@RequestParam long id){
        return hangarService.get(id);
    }

    @PostMapping("/add")
    public void add(@RequestBody Machine machine) {
        hangarService.add(machine);
    }

    @PutMapping("/edit")
    public void edit(@RequestBody Machine machine) {
        hangarService.edit(machine);
    }

    @DeleteMapping("/remove")
    public void removeById(@RequestParam long id, @RequestParam int count) {
        hangarService.remove(id, count);
    }
}
