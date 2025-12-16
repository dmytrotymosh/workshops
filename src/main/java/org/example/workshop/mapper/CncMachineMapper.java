package org.example.workshop.mapper;

import org.example.workshop.dto.cncMachine.*;
import org.example.workshop.model.CncMachine;

public class CncMachineMapper {
    public static CncMachine addDtoToEntity(AddCncMachineRequest request) {
        CncMachine entity = new CncMachine();
        entity.setModel(request.getModel());
        entity.setMaker(request.getMaker());
        entity.setCountryOfOrigin(request.getCountryOfOrigin());
        entity.setLength(request.getLength());
        entity.setHeight(request.getHeight());
        entity.setWidth(request.getWidth());
        entity.setWeight(request.getWeight());
        entity.setVoltage(request.getVoltage());
        entity.setMaxPowerConsumption(request.getMaxPowerConsumption());
        entity.setCommandLanguage(request.getCommandLanguage());
        entity.setOperatingSystem(request.getOperatingSystem());
        entity.setTableWorkArea(request.getTableWorkArea());
        return entity;
    }

    public static CncMachine uploadDtoToEntity(CncMachineUploadDto dto) {
        CncMachine entity = new CncMachine();
        entity.setMaker(dto.getMaker());
        entity.setModel(dto.getModel());
        entity.setCountryOfOrigin(dto.getCountryOfOrigin());
        entity.setLength(dto.getLength());
        entity.setHeight(dto.getHeight());
        entity.setWidth(dto.getWidth());
        entity.setWeight(dto.getWeight());
        entity.setTableWorkArea(dto.getTableWorkArea());
        entity.setVoltage(dto.getVoltage());
        entity.setMaxPowerConsumption(dto.getMaxPowerConsumption());
        entity.setOperatingSystem(dto.getOperatingSystem());
        entity.setCommandLanguage(dto.getCommandLanguage());
        return entity;
    }

    public static CncMachineResponse entityToResponseDto(CncMachine entity) {
        CncMachineResponse response = new CncMachineResponse();
        response.setId(entity.getId());
        response.setMaker(entity.getMaker());
        response.setModel(entity.getModel());
        response.setCountryOfOrigin(entity.getCountryOfOrigin());
        response.setLength(entity.getLength());
        response.setHeight(entity.getHeight());
        response.setWidth(entity.getWidth());
        response.setWeight(entity.getWeight());
        response.setTableWorkArea(entity.getTableWorkArea());
        response.setVoltage(entity.getVoltage());
        response.setMaxPowerConsumption(entity.getMaxPowerConsumption());
        response.setOperatingSystem(entity.getOperatingSystem());
        response.setCommandLanguage(entity.getCommandLanguage());
        response.setUpdated(entity.getUpdated());
        response.setCreated(entity.getCreated());
        response.setWorkshop(WorkshopMapper.entityToResponseDto(entity.getWorkshop()));
        return response;
    }

    public static CncMachineShortResponse entityToShortResponseDto(CncMachine entity) {
        CncMachineShortResponse response = new CncMachineShortResponse();
        response.setId(entity.getId());
        response.setWorkshopId(entity.getWorkshop().getId());
        response.setWorkshopCode(entity.getWorkshop().getCode());
        response.setLength(entity.getLength());
        response.setHeight(entity.getHeight());
        response.setWidth(entity.getWidth());
        response.setUpdated(entity.getUpdated());
        response.setCreated(entity.getCreated());
        return response;
    }

    public static CncMachineCsvToWriteDto entityToCsvDto(CncMachine cm) {
       CncMachineCsvToWriteDto dto = new CncMachineCsvToWriteDto();
       dto.setHeight(cm.getHeight());
       dto.setLength(cm.getLength());
       dto.setWidth(cm.getWidth());
       dto.setWeight(cm.getWeight());
       dto.setVoltage(cm.getVoltage());
       dto.setMaxPowerConsumption(cm.getMaxPowerConsumption());
       return dto;
    }
}
