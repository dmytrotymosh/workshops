package org.example.workshop.service;

import org.example.workshop.dto.workshop.AddWorkshopRequest;
import org.example.workshop.dto.workshop.UpdateWorkshopRequest;
import org.example.workshop.dto.workshop.WorkshopResponse;
import org.example.workshop.exception.NotFoundException;
import org.example.workshop.exception.UniquenessException;
import org.example.workshop.mapper.WorkshopMapper;
import org.example.workshop.model.Workshop;
import org.example.workshop.repository.WorkshopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class WorkshopService {
    private final WorkshopRepository workshopRepository;

    @Autowired
    public WorkshopService(WorkshopRepository workshopRepository) {
        this.workshopRepository = workshopRepository;
    }

    public List<WorkshopResponse> findAll() {
        List<Workshop> workshops = workshopRepository.findAll();
        if (workshops.isEmpty()) {
            throw new NotFoundException("No workshops found");
        }
        List<WorkshopResponse> result = new ArrayList<>();
        for (Workshop workshop : workshops) {
            result.add(WorkshopMapper.entityToResponseDto(workshop));
        }
        return result;
    }

    public WorkshopResponse add(AddWorkshopRequest request) {
        Workshop workshop = WorkshopMapper.addDtoToEntity(request);
        if (workshopRepository.existsByCode(workshop.getCode())) {
            throw new UniquenessException("Workshop with code " + workshop.getCode() + " already exists");
        }
        return WorkshopMapper.entityToResponseDto(workshopRepository.save(workshop));
    }

    public WorkshopResponse update(UUID id, UpdateWorkshopRequest request) {
        Workshop w = workshopRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Workshop width id= " + id + " not found")
        );
        if (request.getCode() != null && workshopRepository.existsByCode(request.getCode())) {
            throw new UniquenessException("Workshop with code " + request.getCode() + " already exists");
        }
        Workshop updated = new Workshop();
        updated.setId(id);
        updated.setCode(request.getCode() == null ? w.getCode() : request.getCode());
        updated.setLength(request.getLength() == null ? w.getLength() : request.getLength());
        updated.setHeight(request.getHeight() == null ? w.getHeight() : request.getHeight());
        updated.setWidth(request.getWidth() == null ? w.getWidth() : request.getWidth());
        updated.setAvailablePower(request.getAvailablePower() == null ?
                w.getAvailablePower() : request.getAvailablePower());
        updated.setFloorLoadCapacity(request.getFloorLoadCapacity() == null ?
                w.getFloorLoadCapacity() : request.getFloorLoadCapacity());
        updated.setVoltage(request.getVoltage() == null ?
                w.getVoltage() : request.getVoltage());
        return WorkshopMapper.entityToResponseDto(workshopRepository.save(updated));
    }

    public void delete(UUID id) {
        if (!workshopRepository.existsById(id)) {
            throw new NotFoundException("Workshop width id=" + id + " not found");
        }
        workshopRepository.deleteById(id);
    }
}
