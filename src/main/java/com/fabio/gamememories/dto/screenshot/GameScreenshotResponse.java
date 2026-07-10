package com.fabio.gamememories.dto.screenshot;

import com.fabio.gamememories.entity.GameScreenshot;
import lombok.Data;

@Data
public class GameScreenshotResponse {
    private Long id;
    private Long gameId;
    private String filePath;
    private String originalFileName;
    private String title;
    private String description;
    private String fileUrl;

    public static GameScreenshotResponse from(GameScreenshot screenshot, String baseUrl) {
        GameScreenshotResponse response = new GameScreenshotResponse();
        response.setId(screenshot.getId());
        response.setGameId(screenshot.getGame().getId());
        response.setFilePath(screenshot.getFilePath());
        response.setOriginalFileName(screenshot.getOriginalFileName());
        response.setTitle(screenshot.getTitle());
        response.setDescription(screenshot.getDescription());
        response.setFileUrl(baseUrl + "/files/" + screenshot.getFilePath());
        return response;
    }
}
