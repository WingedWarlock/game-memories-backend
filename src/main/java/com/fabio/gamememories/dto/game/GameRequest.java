package com.fabio.gamememories.dto.game;

import com.fabio.gamememories.enums.GameStatus;
import lombok.Data;

@Data
public class GameRequest {
    private String title;
    private String saga;
    private String genre;
    private String platform;
    private String coverImage;
    private String description;
    private String notes;
    private Boolean favorite;
    private GameStatus status;
}
