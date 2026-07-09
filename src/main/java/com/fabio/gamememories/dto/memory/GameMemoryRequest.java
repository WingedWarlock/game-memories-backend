package com.fabio.gamememories.dto.memory;

import com.fabio.gamememories.enums.MemoryType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class GameMemoryRequest {
    private String title;
    private String description;
    private LocalDate memoryDate;
    private MemoryType type;
}
