package com.fabio.gamememories.controller;

import com.fabio.gamememories.dto.achievement.AchievementRequest;
import com.fabio.gamememories.dto.achievement.AchievementResponse;
import com.fabio.gamememories.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AchievementController {

    private final AchievementService achievementService;

    @GetMapping("/api/games/{gameId}/achievements")
    public ResponseEntity<List<AchievementResponse>> findByGame(@PathVariable Long gameId) {
        return ResponseEntity.ok(achievementService.findByGame(gameId));
    }

    @GetMapping("/api/achievements/{id}")
    public ResponseEntity<AchievementResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(achievementService.findById(id));
    }

    @PostMapping("/api/games/{gameId}/achievements")
    public ResponseEntity<AchievementResponse> create(@PathVariable Long gameId, @RequestBody AchievementRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(achievementService.create(gameId, request));
    }

    @PutMapping("/api/achievements/{id}")
    public ResponseEntity<AchievementResponse> update(@PathVariable Long id, @RequestBody AchievementRequest request) {
        return ResponseEntity.ok(achievementService.update(id, request));
    }

    @DeleteMapping("/api/achievements/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        achievementService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
