package com.fabio.gamememories.controller;

import com.fabio.gamememories.dto.stats.RetrospectiveResponse;
import com.fabio.gamememories.dto.stats.StatsResponse;
import com.fabio.gamememories.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @GetMapping
    public ResponseEntity<StatsResponse> getStats() {
        return ResponseEntity.ok(statsService.getStats());
    }

    @GetMapping("/retrospective-years")
    public ResponseEntity<List<Integer>> getRetrospectiveYears() {
        return ResponseEntity.ok(statsService.getRetrospectiveYears());
    }

    @GetMapping("/retrospective/{year}")
    public ResponseEntity<RetrospectiveResponse> getRetrospective(@PathVariable int year) {
        return ResponseEntity.ok(statsService.getRetrospective(year));
    }
}
