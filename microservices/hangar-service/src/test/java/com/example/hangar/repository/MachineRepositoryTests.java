package com.example.hangar.repository;

import com.example.hangar.model.Machine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static com.example.hangar.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class MachineRepositoryTests {

    @Autowired
    private MachineRepository machineRepository;

    @Test
    public void testSaveMachine() {
        // Arrange
        Machine machine = getTestMachineA();

        // Act
        Machine savedMachine = machineRepository.save(machine);

        // Assert
        assertNotNull(savedMachine.getId());
        assertEquals(machine.getType(), savedMachine.getType());
        assertEquals(machine.getModel(), savedMachine.getModel());
        assertEquals(machine.getPrice(), savedMachine.getPrice());
    }

    @Test
    public void testFindById() {
        // Arrange
        Machine machine = getTestMachineA();
        Machine savedMachine = machineRepository.save(machine);

        // Act
        Optional<Machine> foundMachine = machineRepository.findById(savedMachine.getId());

        // Assert
        assertTrue(foundMachine.isPresent());
        assertEquals(machine.getType(), foundMachine.get().getType());
        assertEquals(machine.getModel(), foundMachine.get().getModel());
        assertEquals(machine.getPrice(), foundMachine.get().getPrice());
    }

    @Test
    public void testFindAll() {
        // Arrange
        Machine machine1 = getTestMachineA();
        machineRepository.save(machine1);

        Machine machine2 = getTestMachineB();
        machineRepository.save(machine2);

        // Act
        List<Machine> machines = machineRepository.findAll();

        // Assert
        assertEquals(2, machines.size());
        assertEquals(machine1.getModel(), machines.get(0).getModel());
        assertEquals(machine2.getModel(), machines.get(1).getModel());
    }

    @Test
    public void testDelete() {
        // Arrange
        Machine machine = getTestMachineA();
        Machine savedMachine = machineRepository.save(machine);

        // Act
        machineRepository.deleteById(savedMachine.getId());
        Optional<Machine> deletedMachine = machineRepository.findById(savedMachine.getId());

        // Assert
        assertFalse(deletedMachine.isPresent());
    }
}
