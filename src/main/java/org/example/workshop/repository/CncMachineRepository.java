package org.example.workshop.repository;

import org.example.workshop.model.CncMachine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CncMachineRepository extends JpaRepository<CncMachine, UUID> {
    Page<CncMachine> findByWorkshopId(UUID workshopId, Pageable pageable);
    List<CncMachine> findByWorkshopId(UUID workshopId, Sort sort);
}
