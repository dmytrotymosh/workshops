package org.example.workshop.mapper;

import org.example.workshop.dto.workshop.AddWorkshopRequest;
import org.example.workshop.dto.workshop.WorkshopResponse;
import org.example.workshop.model.Workshop;

public class WorkshopMapper {
    public static Workshop addDtoToEntity(AddWorkshopRequest dto) {
        Workshop workshop = new Workshop();
        workshop.setCode(dto.getCode());
        workshop.setHeight(dto.getHeight());
        workshop.setWidth(dto.getWidth());
        workshop.setLength(dto.getLength());
        workshop.setAvailablePower(dto.getAvailablePower());
        workshop.setVoltage(dto.getVoltage());
        workshop.setFloorLoadCapacity(dto.getFloorLoadCapacity());
        return workshop;
    }

    public static WorkshopResponse entityToResponseDto(Workshop workshop) {
        WorkshopResponse workshopResponse = new WorkshopResponse();
        workshopResponse.setCode(workshop.getCode());
        workshopResponse.setId(workshop.getId());
        workshopResponse.setHeight(workshop.getHeight());
        workshopResponse.setWidth(workshop.getWidth());
        workshopResponse.setLength(workshop.getLength());
        workshopResponse.setAvailablePower(workshop.getAvailablePower());
        workshopResponse.setFloorLoadCapacity(workshop.getFloorLoadCapacity());
        workshopResponse.setVoltage(workshop.getVoltage());
        workshopResponse.setCreated(workshop.getCreated());
        workshopResponse.setUpdated(workshop.getUpdated());
        return workshopResponse;
    }
}
