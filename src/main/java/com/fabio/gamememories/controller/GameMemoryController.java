package com.fabio.gamememories.controller;

import com.fabio.gamememories.dto.memory.GameMemoryRequest;
import com.fabio.gamememories.dto.memory.GameMemoryResponse;
import com.fabio.gamememories.service.GameMemoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GameMemoryController {

    private final GameMemoryService gameMemoryService;

    @GetMapping("/api/games/{gameId}/memories")
    public ResponseEntity<List<GameMemoryResponse>> findByGame(@PathVariable Long gameId) {
        return ResponseEntity.ok(gameMemoryService.findByGame(gameId));
    }

    @GetMapping("/api/memories/{id}")
    public ResponseEntity<GameMemoryResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(gameMemoryService.findById(id));
    }

    @PostMapping("/api/games/{gameId}/memories")
    public ResponseEntity<GameMemoryResponse> create(@PathVariable Long gameId, @RequestBody GameMemoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gameMemoryService.create(gameId, request));
    }

    @PutMapping("/api/memories/{id}")
    public ResponseEntity<GameMemoryResponse> update(@PathVariable Long id, @RequestBody GameMemoryRequest request) {
        return ResponseEntity.ok(gameMemoryService.update(id, request));
    }

    @DeleteMapping("/api/memories/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gameMemoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
