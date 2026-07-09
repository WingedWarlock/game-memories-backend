package com.fabio.gamememories.dto.memory;

import com.fabio.gamememories.entity.GameMemory;
import com.fabio.gamememories.enums.MemoryType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class GameMemoryResponse {
    private Long id;
    private Long gameId;
    private String title;
    private String description;
    private LocalDate memoryDate;
    private MemoryType type;

    public static GameMemoryResponse from(GameMemory memory) {
        GameMemoryResponse response = new GameMemoryResponse();
        response.setId(memory.getId());
        response.setGameId(memory.getGame().getId());
        response.setTitle(memory.getTitle());
        response.setDescription(memory.getDescription());
        response.setMemoryDate(memory.getMemoryDate());
        response.setType(memory.getType());
        return response;
    }
}
