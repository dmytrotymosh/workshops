package org.example.workshop.controller;

import jakarta.validation.Valid;
import org.example.workshop.dto.cncMachine.*;
import org.example.workshop.mapper.CncMachineMapper;
import org.example.workshop.model.CncMachine;
import org.example.workshop.service.CncMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cnc-machine")
public class CncMachineController {
    private final CncMachineService cncMachineService;

    @Autowired
    public CncMachineController(CncMachineService cncMachineService) {
        this.cncMachineService = cncMachineService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CncMachineResponse>> getAll() {
        return new ResponseEntity<>(cncMachineService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CncMachineResponse> get(@PathVariable final UUID id) {
        return new ResponseEntity<>(cncMachineService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<CncMachineResponse> add(@Valid @RequestBody final AddCncMachineRequest request) {
        return new ResponseEntity<>(cncMachineService.add(request), HttpStatus.CREATED);
    }

    @PostMapping("/_list")
    public ResponseEntity<PaginationCncMachineResponse> getPages(
            @Valid @RequestBody PaginationCncMachineRequest request) {
        return new ResponseEntity<>(cncMachineService.list(request), HttpStatus.OK);
    }

    @PostMapping("/_report")
    public ResponseEntity<StreamingResponseBody> report(@Valid @RequestBody SortCncMachineRequest request) {
        StreamingResponseBody body = cncMachineService.report(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"data.csv\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(body);
    }

    @PostMapping("/upload")
    public ResponseEntity<CncMachineUploadResponse> uploadFromFile(@RequestParam("file") MultipartFile file) {
        CncMachineUploadResponse response = cncMachineService.uploadFromFile(file);
        return new ResponseEntity<>(response, response.getWritten() >= 1 ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CncMachineResponse> update(
            @PathVariable final UUID id, @Valid @RequestBody final UpdateCncMachineRequest request) {
        return new ResponseEntity<>(cncMachineService.update(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final UUID id) {
        cncMachineService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
