package com.fabio.gamememories.service;

import com.fabio.gamememories.dto.screenshot.GameScreenshotResponse;
import com.fabio.gamememories.entity.Game;
import com.fabio.gamememories.entity.GameScreenshot;
import com.fabio.gamememories.enums.HistoryEventType;
import com.fabio.gamememories.exception.NotFoundException;
import com.fabio.gamememories.repository.GameRepository;
import com.fabio.gamememories.repository.GameScreenshotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameScreenshotService {

    private final GameScreenshotRepository gameScreenshotRepository;
    private final GameRepository gameRepository;
    private final StorageService storageService;
    private final HistoryService historyService;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    public List<GameScreenshotResponse> findByGame(Long gameId) {
        getGameOrThrow(gameId);
        return gameScreenshotRepository.findByGameId(gameId).stream()
                .map(s -> GameScreenshotResponse.from(s, baseUrl))
                .toList();
    }

    public GameScreenshotResponse upload(Long gameId, MultipartFile file, String title, String description) {
        Game game = getGameOrThrow(gameId);
        String filePath = storageService.save(file, "screenshots");

        GameScreenshot screenshot = GameScreenshot.builder()
                .game(game)
                .filePath(filePath)
                .originalFileName(file.getOriginalFilename())
                .title(title)
                .description(description)
                .build();

        GameScreenshot saved = gameScreenshotRepository.save(screenshot);
        historyService.record(HistoryEventType.SCREENSHOT_ADDED, game.getId(), game.getTitle(),
                "Novo screenshot adicionado a " + game.getTitle() + ".");
        return GameScreenshotResponse.from(saved, baseUrl);
    }

    public void delete(Long id) {
        GameScreenshot screenshot = getOrThrow(id);
        storageService.delete(screenshot.getFilePath());
        gameScreenshotRepository.deleteById(id);
    }

    private GameScreenshot getOrThrow(Long id) {
        return gameScreenshotRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Screenshot não encontrado: " + id));
    }

    private Game getGameOrThrow(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new NotFoundException("Game não encontrado: " + gameId));
    }
}
