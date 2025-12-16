package org.example.workshop.service;

import com.opencsv.CSVWriter;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.example.workshop.dto.cncMachine.*;
import org.example.workshop.exception.*;
import org.example.workshop.mapper.CncMachineMapper;
import org.example.workshop.model.CncMachine;
import org.example.workshop.model.Workshop;
import org.example.workshop.repository.CncMachineRepository;
import org.example.workshop.repository.WorkshopRepository;
import org.example.workshop.service.validation.CncMachineValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Service
public class CncMachineService {
    private final WorkshopRepository workshopRepository;
    private final CncMachineRepository cncMachineRepository;
    private final ObjectMapper objectMapper;
    private final Validator validator;

    @Autowired
    public CncMachineService(
            CncMachineRepository cncMachineRepository,
            WorkshopRepository workshopRepository, Validator validator) {
        this.cncMachineRepository = cncMachineRepository;
        this.workshopRepository = workshopRepository;
        this.validator = validator;
        this.objectMapper = new ObjectMapper();
    }

    public List<CncMachineResponse> getAll() {
        List<CncMachine> cncMachines = cncMachineRepository.findAll();
        if (cncMachines.isEmpty()) {
            throw new NotFoundException("No CNC Machines found");
        }
        List<CncMachineResponse> responses = new ArrayList<>();
        for (CncMachine cncMachine : cncMachines) {
            responses.add(CncMachineMapper.entityToResponseDto(cncMachine));
        }
        return responses;
    }

    public CncMachineResponse findById(UUID id) {
        if (cncMachineRepository.findById(id).isEmpty()) {
            throw new NotFoundException("CNC Machine with id=" + id + " not found");
        }
        return CncMachineMapper.entityToResponseDto(cncMachineRepository.findById(id).get());
    }

    public CncMachineResponse add(AddCncMachineRequest request) {
        Workshop workshop = workshopRepository.findById(request.getWorkshopId()).orElseThrow(
                () -> new NotFoundException("Workshop with id=" + request.getWorkshopId() + " not found")
        );
        CncMachine cncMachine = CncMachineMapper.addDtoToEntity(request);
        cncMachine.setWorkshop(workshop);
        List<CncMachineValueOutOfTheBoundsError> errors = CncMachineValidator.validate(
                workshop.getCncMachines(), cncMachine, workshop);
        if (!errors.isEmpty()) {
            throw new CncMachineValueOutOfTheBoundsException(errors);
        }
        return CncMachineMapper.entityToResponseDto(cncMachineRepository.save(cncMachine));
    }

    public CncMachineResponse update(UUID id, UpdateCncMachineRequest request) {
        CncMachine cm = cncMachineRepository.findById(id).orElseThrow(
                () -> new NotFoundException("CNC Machine with id=" + id + " not found")
        );
        Workshop workshop;
        if (request.getWorkshopId() == null) {
            workshop = cm.getWorkshop();
        } else {
            workshop = workshopRepository.findById(request.getWorkshopId()).orElseThrow(
                    () -> new NotFoundException("Workshop with id=" + request.getWorkshopId() + " not found")
            );
        }
        CncMachine updated = getUpdated(id, request, cm);
        List<CncMachineValueOutOfTheBoundsError> errors = CncMachineValidator.validate(
                workshop.getCncMachines().stream().filter(cncMachine -> cncMachine.getId() != id).toList(),
                updated, workshop);
        if (!errors.isEmpty()) {
            throw new CncMachineValueOutOfTheBoundsException(errors);
        }
        updated.setWorkshop(workshop);
        return CncMachineMapper.entityToResponseDto(cncMachineRepository.save(updated));
    }

    private static CncMachine getUpdated(UUID id, UpdateCncMachineRequest request, CncMachine m) {
        CncMachine updated = new CncMachine();
        updated.setId(id);
        updated.setLength(request.getLength() == null ? m.getLength() : request.getLength());
        updated.setWidth(request.getWidth() == null ? m.getWidth() : request.getWidth());
        updated.setHeight(request.getHeight() == null ? m.getHeight() : request.getHeight());
        updated.setWeight(request.getWeight() == null ? m.getWeight() : request.getWeight());
        updated.setVoltage(request.getVoltage() == null ?
                m.getVoltage() : request.getVoltage());
        updated.setMaxPowerConsumption(request.getMaxPowerConsumption() == null ?
                m.getMaxPowerConsumption() : request.getMaxPowerConsumption());
        updated.setModel(request.getModel() == null ? m.getModel() : request.getModel());
        updated.setMaker(request.getMaker() == null ? m.getMaker() : request.getMaker());
        updated.setCountryOfOrigin(request.getCountryOfOrigin() == null ?
                m.getCountryOfOrigin() : request.getCountryOfOrigin());
        updated.setTableWorkArea(request.getTableWorkArea() == null ?
                m.getTableWorkArea() : request.getTableWorkArea());
        updated.setCommandLanguage(request.getCommandLanguage() == null ?
                m.getCommandLanguage() : request.getCommandLanguage());
        updated.setOperatingSystem(request.getOperatingSystem() == null ?
                m.getOperatingSystem() : request.getOperatingSystem());
        return updated;
    }

    public void delete(UUID id) {
        if (!cncMachineRepository.existsById(id)) {
            throw new NotFoundException("CNC Machine with id=" + id + " not found");
        }
        cncMachineRepository.deleteById(id);
    }

    public PaginationCncMachineResponse list(PaginationCncMachineRequest request) {
        Sort sort = getSortFromCncMachineSortRequest(request);
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);
        if (!workshopRepository.existsById(request.getWorkshopId())) {
            throw new NotFoundException("Workshop with id=" + request.getWorkshopId() + " not found");
        }
        Page<CncMachine> page = cncMachineRepository.findByWorkshopId(request.getWorkshopId(), pageable);
        List<CncMachineShortResponse> responseList = page.getContent()
                .stream()
                .map(CncMachineMapper::entityToShortResponseDto)
                .toList();
        return new PaginationCncMachineResponse(responseList, page.getTotalPages());
    }

    public StreamingResponseBody report(SortCncMachineRequest request) {
        Sort sort = getSortFromCncMachineSortRequest(request);
        if (!workshopRepository.existsById(request.getWorkshopId())) {
            throw new NotFoundException("Workshop with id=" + request.getWorkshopId() + " not found");
        }
        List<CncMachine> cncMachines = cncMachineRepository.findByWorkshopId(request.getWorkshopId(), sort);
        return outputStream -> {
            try (Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
                writeCsv(cncMachines, writer);
            }
        };
    }

    public void writeCsv(List<CncMachine> data, Writer writer) {
        try {
            List<CncMachineCsvToWriteDto> csvData = data.stream()
                    .map(CncMachineMapper::entityToCsvDto)
                    .toList();
            HeaderColumnNameMappingStrategy<CncMachineCsvToWriteDto> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(CncMachineCsvToWriteDto.class);
            StatefulBeanToCsv<CncMachineCsvToWriteDto> beanToCsv =
                    new StatefulBeanToCsvBuilder<CncMachineCsvToWriteDto>(writer)
                            .withMappingStrategy(strategy)
                            .withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER)
                            .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                            .build();
            beanToCsv.write(csvData);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            throw new CsvFileWritingException(e.getMessage());
        }
    }

    private Sort getSortFromCncMachineSortRequest(SortCncMachineRequest request) {
        Set<String> ALLOWED_SORT_FIELDS = Set.of("height", "width", "length", "weight",
                "maxPowerConsumption", "voltage");
        List<Sort.Order> orders = new ArrayList<>();
        if (request.getFields().isEmpty()) {
            return Sort.unsorted();
        }
        for (SortingField field : request.getFields()) {
            if (field.getName() == null) {
                continue;
            }
            if (!ALLOWED_SORT_FIELDS.contains(field.getName())) {
                throw new UnknownFieldException("Invalid sort field: " + field.getName());
            }
            orders.add(Boolean.TRUE.equals(field.getDescending())
                    ? Sort.Order.desc(field.getName())
                    : Sort.Order.asc(field.getName()));
        }
        return Sort.by(orders);
    }

    public CncMachineUploadResponse uploadFromFile(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            List<CncMachineUploadDto> cncMachines = objectMapper.readValue(
                    bytes, new TypeReference<List<CncMachineUploadDto>>() {});
            int written = 0, unwritten = 0;
            for (CncMachineUploadDto uploadDto : cncMachines) {
                Set<ConstraintViolation<CncMachineUploadDto>> violations = validator.validate(uploadDto);
                if (!violations.isEmpty()) {
                    unwritten++;
                    continue;
                }
                CncMachine cm = CncMachineMapper.uploadDtoToEntity(uploadDto);
                if (!workshopRepository.existsById(uploadDto.getWorkshopId())) {
                    unwritten++;
                    continue;
                }
                cm.setWorkshop(workshopRepository.findById(uploadDto.getWorkshopId()).orElse(null));
                List<CncMachineValueOutOfTheBoundsError> errors = CncMachineValidator.validate(
                        cm.getWorkshop().getCncMachines().stream().filter(
                                cncMachine -> !Objects.equals(cncMachine.getId(), cm.getId())).toList(),
                        cm, cm.getWorkshop());
                if (!errors.isEmpty()) {
                    unwritten++;
                } else {
                    cncMachineRepository.save(cm);
                    written++;
                }
            }
            return new CncMachineUploadResponse(written, unwritten);
        } catch (Exception e) {
            throw new UploadedFileReadingException(e.getMessage());
        }
    }
}
