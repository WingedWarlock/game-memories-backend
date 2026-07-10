package com.fabio.gamememories.controller;

import com.fabio.gamememories.dto.cover.GameCoverResponse;
import com.fabio.gamememories.service.GameCoverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GameCoverController {

    private final GameCoverService gameCoverService;

    @GetMapping("/api/games/{gameId}/covers")
    public ResponseEntity<List<GameCoverResponse>> findByGame(@PathVariable Long gameId) {
        return ResponseEntity.ok(gameCoverService.findByGame(gameId));
    }

    @PostMapping("/api/games/{gameId}/covers")
    public ResponseEntity<GameCoverResponse> upload(
            @PathVariable Long gameId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "displayOrder", required = false) Integer displayOrder) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(gameCoverService.upload(gameId, file, title, displayOrder));
    }

    @DeleteMapping("/api/covers/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gameCoverService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
