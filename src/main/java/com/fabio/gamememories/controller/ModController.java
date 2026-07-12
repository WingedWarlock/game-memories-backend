package com.fabio.gamememories.controller;

import com.fabio.gamememories.dto.mod.ModRequest;
import com.fabio.gamememories.dto.mod.ModResponse;
import com.fabio.gamememories.service.ModService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ModController {

    private final ModService modService;

    @GetMapping("/api/games/{gameId}/mods")
    public ResponseEntity<List<ModResponse>> findByGame(@PathVariable Long gameId) {
        return ResponseEntity.ok(modService.findByGame(gameId));
    }

    @GetMapping("/api/mods/{id}")
    public ResponseEntity<ModResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(modService.findById(id));
    }

    @PostMapping("/api/games/{gameId}/mods")
    public ResponseEntity<ModResponse> create(@PathVariable Long gameId, @RequestBody ModRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(modService.create(gameId, request));
    }

    @PutMapping("/api/mods/{id}")
    public ResponseEntity<ModResponse> update(@PathVariable Long id, @RequestBody ModRequest request) {
        return ResponseEntity.ok(modService.update(id, request));
    }

    @DeleteMapping("/api/mods/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        modService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
