package com.example.militarymachineshangar.repository;

import com.example.militarymachineshangar.models.jpa.Machine;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

//@Transactional      // Database rollback before each test
//@Rollback(true)     //

@Log

@SpringBootTest
public class MachineRepositoryTest {

    @Autowired
    private MachineRepository machineRepository;


    @Transactional
    @Test
    void addEntity_tank() {
        Machine tank = TestUtils.getTestTank();

        Machine result = machineRepository.save(tank);

        assertEquals(tank, result);
    }

    @Transactional
    @Test
    void addEntity_cwh() {
        Machine cwh = TestUtils.getTestCWH();

        Machine result = machineRepository.save(cwh);

        assertEquals(cwh, result);
    }

    @Transactional
    @Test
    void addEntities_10() {
        int numberOfMachines = 10;

        for (int i = 0; i < numberOfMachines; i++) {
            Machine tank = TestUtils.getTestTank();
            machineRepository.save(tank);
        }

        assertEquals(numberOfMachines, machineRepository.findAll().size());
    }

    @Transactional
    @Test
    void addEntities_100() {
        int numberOfMachines = 100;

        for (int i = 0; i < numberOfMachines; i++) {
            Machine cwh = TestUtils.getTestCWH();
            machineRepository.save(cwh);
        }

        assertEquals(numberOfMachines, machineRepository.findAll().size());
    }

    @Transactional
    @Test
    void getEntityById() {
        Machine cwh = TestUtils.getTestCWH();

        machineRepository.save(cwh);

        Optional<Machine> optResult = machineRepository.findById(cwh.getId());

        assertTrue(optResult.isPresent());

        Machine result = optResult.get();

        assertEquals(cwh, result);
    }

    @Transactional
    @Test
    void deleteEntity() {
        Machine tank = TestUtils.getTestTank();

        machineRepository.save(tank);

        assertEquals(1, machineRepository.findAll().size());

        machineRepository.delete(tank);

        assertEquals(0, machineRepository.findAll().size());
    }

    @Transactional
    @Test
    void deleteEntityById() {
        Machine tank = TestUtils.getTestTank();

        machineRepository.save(tank);

        assertEquals(1, machineRepository.findAll().size());

        machineRepository.deleteById(tank.getId());

        assertEquals(0, machineRepository.findAll().size());
    }
}
