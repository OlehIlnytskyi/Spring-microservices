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

    public String initValues() {
        if (isDataInserted) return "Data is already inserted!";

        hangarRepository.save(new Machine(1, MilitaryMachineType.COMBAT_WHEELED_VEHICLES, "BMP-2", LocalDate.of(1980, 4, 12), LocalDate.of(2025, 4, 12), 200));
        hangarRepository.save(new Machine(2, MilitaryMachineType.COMBAT_WHEELED_VEHICLES, "BTR-4", LocalDate.of(2014, 2, 23), LocalDate.of(2049, 2, 23), 125));
        hangarRepository.save(new Machine(3, MilitaryMachineType.TANK, "Leopard 2A4", LocalDate.of(1979, 12, 25), LocalDate.of(2035, 12, 25), 50));
        hangarRepository.save(new Machine(4, MilitaryMachineType.TANK, "T-64BM", LocalDate.of(1967, 1, 2), LocalDate.of(2010, 1, 2), 75));

        isDataInserted = true;

        return "Data successfully inserted!";
    }

    public Catalog<Machine> getAll() {
        return new Catalog<>(hangarRepository.findAll());
    }

    public Optional<Machine> get(long id) {
            return hangarRepository.findById(id);
    }

    public void add(Machine machine) {
        hangarRepository.save(machine);
    }

    public void edit(Machine newMachineData) {

        // Check: machine with same id is present
        get(newMachineData.getId()).orElseThrow();

        // Result: editing data
        hangarRepository.save(newMachineData);
    }

    public void remove(long id, int count) {

        // Check: machine with selected id is present
        // Get: machine to edit (REMOVE)
        Machine machine = get(id).orElseThrow();

        int currentCount = machine.getCount();

        // Check: hangar has enough machines
        if (currentCount < count){
            return;
        }

        // Check: client ordered all machines from hangar
        if (currentCount == count){
            hangarRepository.deleteById(id);
            return;
        }

        // Result: removing selected count of machines
        machine.setCount(currentCount - count);
        edit(machine);
    }
}
