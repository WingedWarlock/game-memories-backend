package com.fabio.gamememories.controller;

import com.fabio.gamememories.dto.saga.SagaRequest;
import com.fabio.gamememories.dto.saga.SagaResponse;
import com.fabio.gamememories.service.SagaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sagas")
@RequiredArgsConstructor
public class SagaController {

    private final SagaService sagaService;

    @GetMapping
    public ResponseEntity<List<SagaResponse>> findAll() {
        return ResponseEntity.ok(sagaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SagaResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(sagaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SagaResponse> create(@RequestBody SagaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sagaService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SagaResponse> update(@PathVariable Long id, @RequestBody SagaRequest request) {
        return ResponseEntity.ok(sagaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        sagaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
