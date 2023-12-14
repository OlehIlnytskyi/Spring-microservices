package com.example.hangar.controller;

import com.example.hangar.TestUtils;
import com.example.hangar.dto.*;
import com.example.hangar.service.HangarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(HangarController.class)
public class HangarControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HangarService hangarService;

    @Autowired
    private Environment environment;

    @Test
    void testDatabaseUsed() {
        String databaseUrl = environment.getProperty("spring.datasource.url");
        System.out.println("Database URL: " + databaseUrl);
        System.out.println("Class: " + environment.getClass());
    }

    @Test
    void postHangarShouldReturnOk() throws Exception {
        // Arrange
        List<MachineRequest> machineRequests =
                Collections.singletonList(TestUtils.getTestMachineRequest());

        when(hangarService.addMachine(machineRequests))
                .thenReturn(ResponseEntity.ok().build());

        // Act & Assert
        mockMvc.perform(post("/api/hangar/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{}]"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getHangarShouldReturnMachineResponse() throws Exception {
        // Arrange
        MachineResponse machineResponse = TestUtils.getTestMachineResponse();

        when(hangarService.getMachineById(machineResponse.getId()))
                .thenReturn(ResponseEntity.ok(machineResponse));

        // Act & Assert
        mockMvc.perform(get("/api/hangar/get")
                        .param("machineId", String.valueOf(machineResponse.getId())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(machineResponse.getId()))
                .andExpect(jsonPath("$.type").value(machineResponse.getType().toString()))
                .andExpect(jsonPath("$.model").value(machineResponse.getModel()))
                .andExpect(jsonPath("$.price").value(machineResponse.getPrice()));
    }

    @Test
    void getAllHangarShouldReturnMachinesListResponse() throws Exception {
        // Arrange
        MachineResponse machineResponse = TestUtils.getTestMachineResponse();
        MachineResponseListWrapper machineResponseListWrapper = new MachineResponseListWrapper(Collections.singletonList(machineResponse));

        when(hangarService.getAllMachinesInWrapper())
                .thenReturn(ResponseEntity.ok(machineResponseListWrapper));

        // Act & Assert
        mockMvc.perform(get("/api/hangar/getAll"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.machineResponses[0].id").value(machineResponse.getId()))
                .andExpect(jsonPath("$.machineResponses[0].type").value(machineResponse.getType().toString()))
                .andExpect(jsonPath("$.machineResponses[0].model").value(machineResponse.getModel()))
                .andExpect(jsonPath("$.machineResponses[0].price").value(machineResponse.getPrice()));
    }

    @Test
    void deleteHangarShouldReturnNoContent() throws Exception {
        // Arrange
        Long machineId = 1L;
        when(hangarService.deleteMachineById(machineId))
                .thenReturn(ResponseEntity.noContent().build());

        // Act & Assert
        mockMvc.perform(delete("/api/hangar/delete")
                        .param("machineId", String.valueOf(machineId)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
