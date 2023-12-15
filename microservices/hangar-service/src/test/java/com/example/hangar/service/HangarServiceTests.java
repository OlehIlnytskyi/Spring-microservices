package com.example.hangar.service;

import com.example.hangar.dto.MachineRequest;
import com.example.hangar.dto.MachineResponse;
import com.example.hangar.dto.MachineResponseListWrapper;
import com.example.hangar.model.Machine;
import com.example.hangar.repository.MachineRepository;
import com.example.hangar.service.impl.HangarServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.hangar.utils.MachineMapper.mapToBase;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static com.example.hangar.TestUtils.*;

@ExtendWith(MockitoExtension.class)
class HangarServiceTests {

    @Mock
    private MachineRepository machineRepository;

    @InjectMocks
    private HangarService hangarService = new HangarServiceImpl();

    @Test
    void postShouldSaveMachinesAndReturnOk() {
        // Arrange
        List<MachineRequest> machineRequests = Collections.singletonList(
                getTestMachineRequest()
        );

        // Act
        ResponseEntity<Void> result = hangarService.addMachine(machineRequests);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(machineRepository, times(1)).saveAll(any());
    }

    @Test
    void getShouldReturnMachineResponseWhenMachineExists() {
        // Arrange
        Machine testMachine = getTestMachineA();
        when(machineRepository.findById(testMachine.getId()))
                .thenReturn(Optional.of(testMachine));

        // Act
        ResponseEntity<MachineResponse> result = hangarService.getMachineById(testMachine.getId());
        MachineResponse body = result.getBody();

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(body);
        assertEquals(testMachine, mapToBase(body));
    }

    @Test
    void getShouldReturnNotFoundWhenMachineDoesNotExist() {
        // Arrange
        Long machineId = 1L;
        when(machineRepository.findById(machineId))
                .thenReturn(Optional.empty());

        // Act
        ResponseEntity<MachineResponse> result = hangarService.getMachineById(machineId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void getAllShouldReturnMachinesListResponse() {
        // Arrange
        Machine machine = getTestMachineA();
        when(machineRepository.findAll())
                .thenReturn(Collections.singletonList(machine));

        // Act
        List<MachineResponse> result = hangarService.getAllMachines();

        // Assert
        assertNotNull(result);
        assertNotNull(result.get(0));
        assertEquals(machine, mapToBase(result.get(0)));
    }

    @Test
    void getAllMachinesInWrapperShouldReturnWrapperWithValues() {
        // Arrange
        Machine machine = getTestMachineA();
        when(machineRepository.findAll())
                .thenReturn(Collections.singletonList(machine));

        // Act
        ResponseEntity<MachineResponseListWrapper> result = hangarService.getAllMachinesInWrapper();
        MachineResponseListWrapper body = result.getBody();

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        assertNotNull(body);
        assertEquals(1, body.getMachineResponses().size());
        assertEquals(machine, mapToBase(body.getMachineResponses().get(0)));
    }

    @Test
    void deleteByIdShouldDeleteMachineAndReturnOk() {
        // Arrange
        Long machineId = 1L;

        // Act
        ResponseEntity<Void> result = hangarService.deleteMachineById(machineId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(machineRepository, times(1)).deleteById(machineId);
    }
}