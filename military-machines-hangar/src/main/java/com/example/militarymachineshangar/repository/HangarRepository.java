package com.example.militarymachineshangar.repository;

import com.example.militarymachineshangar.models.Machine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HangarRepository extends JpaRepository<Machine, Long> {

}
