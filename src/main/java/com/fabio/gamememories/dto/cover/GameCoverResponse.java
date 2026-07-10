package com.fabio.gamememories.dto.cover;

import com.fabio.gamememories.entity.GameCover;
import lombok.Data;

@Data
public class GameCoverResponse {
    private Long id;
    private Long gameId;
    private String filePath;
    private String originalFileName;
    private Integer displayOrder;
    private String title;
    private String fileUrl;

    public static GameCoverResponse from(GameCover cover, String baseUrl) {
        GameCoverResponse response = new GameCoverResponse();
        response.setId(cover.getId());
        response.setGameId(cover.getGame().getId());
        response.setFilePath(cover.getFilePath());
        response.setOriginalFileName(cover.getOriginalFileName());
        response.setDisplayOrder(cover.getDisplayOrder());
        response.setTitle(cover.getTitle());
        response.setFileUrl(baseUrl + "/files/" + cover.getFilePath());
        return response;
    }
}
