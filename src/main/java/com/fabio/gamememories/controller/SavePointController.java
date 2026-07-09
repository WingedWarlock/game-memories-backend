package com.fabio.gamememories.controller;

import com.fabio.gamememories.dto.savepoint.SavePointRequest;
import com.fabio.gamememories.dto.savepoint.SavePointResponse;
import com.fabio.gamememories.service.SavePointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SavePointController {

    private final SavePointService savePointService;

    @GetMapping("/api/runs/{runId}/savepoints")
    public ResponseEntity<List<SavePointResponse>> findByRun(@PathVariable Long runId) {
        return ResponseEntity.ok(savePointService.findByRun(runId));
    }

    @GetMapping("/api/savepoints/{id}")
    public ResponseEntity<SavePointResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(savePointService.findById(id));
    }

    @PostMapping("/api/runs/{runId}/savepoints")
    public ResponseEntity<SavePointResponse> create(@PathVariable Long runId, @RequestBody SavePointRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(savePointService.create(runId, request));
    }

    @PutMapping("/api/savepoints/{id}")
    public ResponseEntity<SavePointResponse> update(@PathVariable Long id, @RequestBody SavePointRequest request) {
        return ResponseEntity.ok(savePointService.update(id, request));
    }

    @DeleteMapping("/api/savepoints/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        savePointService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
