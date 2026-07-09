package com.fabio.gamememories.controller;

import com.fabio.gamememories.dto.run.RunRequest;
import com.fabio.gamememories.dto.run.RunResponse;
import com.fabio.gamememories.service.RunService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RunController {

    private final RunService runService;

    @GetMapping("/api/games/{gameId}/runs")
    public ResponseEntity<List<RunResponse>> findByGame(@PathVariable Long gameId) {
        return ResponseEntity.ok(runService.findByGame(gameId));
    }

    @GetMapping("/api/runs/{id}")
    public ResponseEntity<RunResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(runService.findById(id));
    }

    @PostMapping("/api/games/{gameId}/runs")
    public ResponseEntity<RunResponse> create(@PathVariable Long gameId, @RequestBody RunRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(runService.create(gameId, request));
    }

    @PutMapping("/api/runs/{id}")
    public ResponseEntity<RunResponse> update(@PathVariable Long id, @RequestBody RunRequest request) {
        return ResponseEntity.ok(runService.update(id, request));
    }

    @DeleteMapping("/api/runs/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        runService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
