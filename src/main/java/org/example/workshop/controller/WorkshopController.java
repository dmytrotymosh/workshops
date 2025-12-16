package org.example.workshop.controller;

import jakarta.validation.Valid;
import org.example.workshop.dto.workshop.AddWorkshopRequest;
import org.example.workshop.dto.workshop.UpdateWorkshopRequest;
import org.example.workshop.dto.workshop.WorkshopResponse;
import org.example.workshop.service.WorkshopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/workshop")
public class WorkshopController {
    private final WorkshopService workshopService;
    @Autowired
    public WorkshopController(WorkshopService workshopService) {
        this.workshopService = workshopService;
    }

    @GetMapping("/")
    public ResponseEntity<List<WorkshopResponse>> getAll() {
        return new ResponseEntity<>(workshopService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<WorkshopResponse> add(@Valid @RequestBody AddWorkshopRequest request) {
        return new ResponseEntity<>(workshopService.add(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkshopResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateWorkshopRequest request
    ) {
        return new ResponseEntity<>(workshopService.update(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        workshopService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
