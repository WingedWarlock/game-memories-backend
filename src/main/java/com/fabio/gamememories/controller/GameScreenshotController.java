package com.fabio.gamememories.controller;

import com.fabio.gamememories.dto.screenshot.GameScreenshotResponse;
import com.fabio.gamememories.service.GameScreenshotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GameScreenshotController {

    private final GameScreenshotService gameScreenshotService;

    @GetMapping("/api/games/{gameId}/screenshots")
    public ResponseEntity<List<GameScreenshotResponse>> findByGame(@PathVariable Long gameId) {
        return ResponseEntity.ok(gameScreenshotService.findByGame(gameId));
    }

    @PostMapping("/api/games/{gameId}/screenshots")
    public ResponseEntity<GameScreenshotResponse> upload(
            @PathVariable Long gameId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(gameScreenshotService.upload(gameId, file, title, description));
    }

    @DeleteMapping("/api/screenshots/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gameScreenshotService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
