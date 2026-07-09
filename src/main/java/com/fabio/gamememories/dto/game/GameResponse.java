package com.fabio.gamememories.dto.game;

import com.fabio.gamememories.entity.Game;
import com.fabio.gamememories.enums.GameStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GameResponse {
    private Long id;
    private String title;
    private String saga;
    private String genre;
    private String platform;
    private String coverImage;
    private String description;
    private String notes;
    private Boolean favorite;
    private GameStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static GameResponse from(Game game) {
        GameResponse response = new GameResponse();
        response.setId(game.getId());
        response.setTitle(game.getTitle());
        response.setSaga(game.getSaga());
        response.setGenre(game.getGenre());
        response.setPlatform(game.getPlatform());
        response.setCoverImage(game.getCoverImage());
        response.setDescription(game.getDescription());
        response.setNotes(game.getNotes());
        response.setFavorite(game.getFavorite());
        response.setStatus(game.getStatus());
        response.setCreatedAt(game.getCreatedAt());
        response.setUpdatedAt(game.getUpdatedAt());
        return response;
    }
}
