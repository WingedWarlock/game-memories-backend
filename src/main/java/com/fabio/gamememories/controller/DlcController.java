package com.fabio.gamememories.controller;

import com.fabio.gamememories.dto.dlc.DlcRequest;
import com.fabio.gamememories.dto.dlc.DlcResponse;
import com.fabio.gamememories.service.DlcService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DlcController {

    private final DlcService dlcService;

    @GetMapping("/api/games/{gameId}/dlcs")
    public ResponseEntity<List<DlcResponse>> findByGame(@PathVariable Long gameId) {
        return ResponseEntity.ok(dlcService.findByGame(gameId));
    }

    @GetMapping("/api/dlcs/{id}")
    public ResponseEntity<DlcResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(dlcService.findById(id));
    }

    @PostMapping("/api/games/{gameId}/dlcs")
    public ResponseEntity<DlcResponse> create(@PathVariable Long gameId, @RequestBody DlcRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dlcService.create(gameId, request));
    }

    @PutMapping("/api/dlcs/{id}")
    public ResponseEntity<DlcResponse> update(@PathVariable Long id, @RequestBody DlcRequest request) {
        return ResponseEntity.ok(dlcService.update(id, request));
    }

    @DeleteMapping("/api/dlcs/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        dlcService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
