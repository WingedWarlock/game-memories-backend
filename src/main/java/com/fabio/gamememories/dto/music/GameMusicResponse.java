package com.fabio.gamememories.dto.music;

import com.fabio.gamememories.entity.GameMusic;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GameMusicResponse {
    private Long id;
    private Long gameId;
    private String filePath;
    private String originalFileName;
    private String title;
    private String artist;
    private String description;
    private String fileUrl;
    private LocalDateTime createdAt;

    public static GameMusicResponse from(GameMusic music, String baseUrl) {
        GameMusicResponse response = new GameMusicResponse();
        response.setId(music.getId());
        response.setGameId(music.getGame().getId());
        response.setFilePath(music.getFilePath());
        response.setOriginalFileName(music.getOriginalFileName());
        response.setTitle(music.getTitle());
        response.setArtist(music.getArtist());
        response.setDescription(music.getDescription());
        response.setFileUrl(baseUrl + "/files/" + music.getFilePath());
        response.setCreatedAt(music.getCreatedAt());
        return response;
    }
}
