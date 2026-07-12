package com.fabio.gamememories.controller;

import com.fabio.gamememories.dto.lifeevent.LifeEventRequest;
import com.fabio.gamememories.dto.lifeevent.LifeEventResponse;
import com.fabio.gamememories.service.LifeEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/life-events")
@RequiredArgsConstructor
public class LifeEventController {

    private final LifeEventService lifeEventService;

    @GetMapping
    public ResponseEntity<List<LifeEventResponse>> findAll() {
        return ResponseEntity.ok(lifeEventService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LifeEventResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(lifeEventService.findById(id));
    }

    @PostMapping
    public ResponseEntity<LifeEventResponse> create(@RequestBody LifeEventRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lifeEventService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LifeEventResponse> update(@PathVariable Long id, @RequestBody LifeEventRequest request) {
        return ResponseEntity.ok(lifeEventService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        lifeEventService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
