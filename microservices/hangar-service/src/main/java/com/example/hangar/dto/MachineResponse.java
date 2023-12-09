package com.example.hangar.dto;

import com.example.hangar.model.MachineType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MachineResponse {
    private Long id;
    private MachineType type;
    private String model;
    private BigDecimal price;
}
