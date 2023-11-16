package com.example.militarymachineshangar.service;

import com.example.militarymachineshangar.models.Catalog;
import com.example.militarymachineshangar.models.Machine;
import com.example.militarymachineshangar.models.MilitaryMachineType;
import com.example.militarymachineshangar.repository.HangarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class HangarService {

    @Autowired
    private HangarRepository hangarRepository;

    private boolean isDataInserted;

    public Catalog<Machine> getAllMachines() {
        return new Catalog<>(hangarRepository.findAll());
    }

    public Optional<Machine> getMachine(long id) {
            return hangarRepository.findById(id);
    }

    public void addMachine(Machine machine) {
        hangarRepository.save(machine);
    }

    public void editMachine(Machine newMachineData) {
        Machine machine = getMachine(newMachineData.getId()).orElseThrow();

        hangarRepository.save(newMachineData);
    }

    public void removeMachine(long id, int count) {
        Machine machine = getMachine(id).orElseThrow();
        int currentCount = machine.getCount();

        if (currentCount < count){
            // Замало
        }

        if (currentCount == count){
            hangarRepository.deleteById(id);
            return;
        }

        machine.setCount(currentCount - count);
        editMachine(machine);
    }

    public String insertData() {
        if (isDataInserted) return "Data is already inserted!";

        hangarRepository.save(new Machine(1, MilitaryMachineType.COMBAT_WHEELED_VEHICLES, "BMP-2", LocalDate.of(1980, 4, 12), LocalDate.of(2025, 4, 12), 200));
        hangarRepository.save(new Machine(2, MilitaryMachineType.COMBAT_WHEELED_VEHICLES, "BTR-4", LocalDate.of(2014, 2, 23), LocalDate.of(2049, 2, 23), 125));
        hangarRepository.save(new Machine(3, MilitaryMachineType.TANK, "Leopard 2A4", LocalDate.of(1979, 12, 25), LocalDate.of(2035, 12, 25), 50));
        hangarRepository.save(new Machine(4, MilitaryMachineType.TANK, "T-64BM", LocalDate.of(1967, 1, 2), LocalDate.of(2010, 1, 2), 75));

        isDataInserted = true;

        return "Data successfully inserted!";
    }
}
