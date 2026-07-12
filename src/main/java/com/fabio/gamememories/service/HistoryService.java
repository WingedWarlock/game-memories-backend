package com.fabio.gamememories.service;

import com.fabio.gamememories.dto.history.HistoryEventResponse;
import com.fabio.gamememories.entity.HistoryEvent;
import com.fabio.gamememories.enums.HistoryEventType;
import com.fabio.gamememories.repository.HistoryEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryEventRepository historyEventRepository;

    public void record(HistoryEventType type, Long gameId, String gameTitle, String description) {
        HistoryEvent event = HistoryEvent.builder()
                .type(type)
                .gameId(gameId)
                .gameTitle(gameTitle)
                .description(description)
                .build();
        historyEventRepository.save(event);
    }

    public List<HistoryEventResponse> findAll() {
        return historyEventRepository.findAllByOrderByOccurredAtDesc().stream()
                .map(HistoryEventResponse::from)
                .toList();
    }
}
