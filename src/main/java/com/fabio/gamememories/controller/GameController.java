package com.fabio.gamememories.controller;

import com.fabio.gamememories.dto.game.GameRequest;
import com.fabio.gamememories.dto.game.GameResponse;
import com.fabio.gamememories.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping
    public ResponseEntity<List<GameResponse>> findAll() {
        return ResponseEntity.ok(gameService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(gameService.findById(id));
    }

    @PostMapping
    public ResponseEntity<GameResponse> create(@RequestBody GameRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gameService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameResponse> update(@PathVariable Long id, @RequestBody GameRequest request) {
        return ResponseEntity.ok(gameService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gameService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
