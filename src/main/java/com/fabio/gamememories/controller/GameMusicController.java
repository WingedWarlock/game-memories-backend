package com.fabio.gamememories.controller;

import com.fabio.gamememories.dto.music.GameMusicResponse;
import com.fabio.gamememories.service.GameMusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GameMusicController {

    private final GameMusicService gameMusicService;

    @GetMapping("/api/games/{gameId}/music")
    public ResponseEntity<List<GameMusicResponse>> findByGame(@PathVariable Long gameId) {
        return ResponseEntity.ok(gameMusicService.findByGame(gameId));
    }

    @PostMapping("/api/games/{gameId}/music")
    public ResponseEntity<GameMusicResponse> upload(
            @PathVariable Long gameId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "artist", required = false) String artist,
            @RequestParam(value = "description", required = false) String description) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(gameMusicService.upload(gameId, file, title, artist, description));
    }

    @DeleteMapping("/api/music/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gameMusicService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
