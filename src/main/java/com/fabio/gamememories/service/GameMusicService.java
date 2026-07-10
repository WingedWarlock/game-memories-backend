package com.fabio.gamememories.service;

import com.fabio.gamememories.dto.music.GameMusicResponse;
import com.fabio.gamememories.entity.Game;
import com.fabio.gamememories.entity.GameMusic;
import com.fabio.gamememories.exception.NotFoundException;
import com.fabio.gamememories.repository.GameMusicRepository;
import com.fabio.gamememories.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameMusicService {

    private final GameMusicRepository gameMusicRepository;
    private final GameRepository gameRepository;
    private final StorageService storageService;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    public List<GameMusicResponse> findByGame(Long gameId) {
        getGameOrThrow(gameId);
        return gameMusicRepository.findByGameId(gameId).stream()
                .map(m -> GameMusicResponse.from(m, baseUrl))
                .toList();
    }

    public GameMusicResponse upload(Long gameId, MultipartFile file, String title, String artist, String description) {
        Game game = getGameOrThrow(gameId);
        String filePath = storageService.save(file, "music");

        GameMusic music = GameMusic.builder()
                .game(game)
                .filePath(filePath)
                .originalFileName(file.getOriginalFilename())
                .title(title)
                .artist(artist)
                .description(description)
                .build();

        return GameMusicResponse.from(gameMusicRepository.save(music), baseUrl);
    }

    public void delete(Long id) {
        GameMusic music = getOrThrow(id);
        storageService.delete(music.getFilePath());
        gameMusicRepository.deleteById(id);
    }

    private GameMusic getOrThrow(Long id) {
        return gameMusicRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Música não encontrada: " + id));
    }

    private Game getGameOrThrow(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new NotFoundException("Game não encontrado: " + gameId));
    }
}
