package com.fabio.gamememories.dto.history;

import com.fabio.gamememories.entity.HistoryEvent;
import com.fabio.gamememories.enums.HistoryEventType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistoryEventResponse {
    private Long id;
    private HistoryEventType type;
    private Long gameId;
    private String gameTitle;
    private String description;
    private LocalDateTime occurredAt;

    public static HistoryEventResponse from(HistoryEvent event) {
        HistoryEventResponse response = new HistoryEventResponse();
        response.setId(event.getId());
        response.setType(event.getType());
        response.setGameId(event.getGameId());
        response.setGameTitle(event.getGameTitle());
        response.setDescription(event.getDescription());
        response.setOccurredAt(event.getOccurredAt());
        return response;
    }
}
