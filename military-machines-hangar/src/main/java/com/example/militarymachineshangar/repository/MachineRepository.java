package com.example.militarymachineshangar.repository;

import com.example.militarymachineshangar.models.jpa.Machine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineRepository extends JpaRepository<Machine, Long> {

    long countByModelId(Long modelId);
}
