package com.fabio.gamememories.controller;

import com.fabio.gamememories.dto.history.HistoryEventResponse;
import com.fabio.gamememories.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping("/api/history")
    public ResponseEntity<List<HistoryEventResponse>> findAll() {
        return ResponseEntity.ok(historyService.findAll());
    }
}
