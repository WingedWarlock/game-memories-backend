package com.fabio.gamememories.service;

import com.fabio.gamememories.dto.cover.GameCoverResponse;
import com.fabio.gamememories.entity.Game;
import com.fabio.gamememories.entity.GameCover;
import com.fabio.gamememories.exception.NotFoundException;
import com.fabio.gamememories.repository.GameCoverRepository;
import com.fabio.gamememories.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameCoverService {

    private final GameCoverRepository gameCoverRepository;
    private final GameRepository gameRepository;
    private final StorageService storageService;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    public List<GameCoverResponse> findByGame(Long gameId) {
        getGameOrThrow(gameId);
        return gameCoverRepository.findByGameIdOrderByDisplayOrderAsc(gameId).stream()
                .map(c -> GameCoverResponse.from(c, baseUrl))
                .toList();
    }

    public GameCoverResponse upload(Long gameId, MultipartFile file, String title, Integer displayOrder) {
        Game game = getGameOrThrow(gameId);
        String filePath = storageService.save(file, "covers");

        GameCover cover = GameCover.builder()
                .game(game)
                .filePath(filePath)
                .originalFileName(file.getOriginalFilename())
                .title(title)
                .displayOrder(displayOrder)
                .build();

        return GameCoverResponse.from(gameCoverRepository.save(cover), baseUrl);
    }

    public void delete(Long id) {
        GameCover cover = getOrThrow(id);
        storageService.delete(cover.getFilePath());
        gameCoverRepository.deleteById(id);
    }

    private GameCover getOrThrow(Long id) {
        return gameCoverRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Capa não encontrada: " + id));
    }

    private Game getGameOrThrow(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new NotFoundException("Game não encontrado: " + gameId));
    }
}
