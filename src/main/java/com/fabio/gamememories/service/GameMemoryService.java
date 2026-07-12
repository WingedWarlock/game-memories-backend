package com.fabio.gamememories.service;

import com.fabio.gamememories.dto.memory.GameMemoryRequest;
import com.fabio.gamememories.dto.memory.GameMemoryResponse;
import com.fabio.gamememories.entity.Game;
import com.fabio.gamememories.entity.GameMemory;
import com.fabio.gamememories.enums.HistoryEventType;
import com.fabio.gamememories.exception.NotFoundException;
import com.fabio.gamememories.repository.GameMemoryRepository;
import com.fabio.gamememories.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameMemoryService {

    private final GameMemoryRepository gameMemoryRepository;
    private final GameRepository gameRepository;
    private final HistoryService historyService;

    public List<GameMemoryResponse> findByGame(Long gameId) {
        getGameOrThrow(gameId);
        return gameMemoryRepository.findByGameId(gameId).stream()
                .map(GameMemoryResponse::from)
                .toList();
    }

    public GameMemoryResponse findById(Long id) {
        return GameMemoryResponse.from(getOrThrow(id));
    }

    public GameMemoryResponse create(Long gameId, GameMemoryRequest request) {
        Game game = getGameOrThrow(gameId);
        GameMemory memory = GameMemory.builder()
                .game(game)
                .title(request.getTitle())
                .description(request.getDescription())
                .memoryDate(request.getMemoryDate())
                .type(request.getType())
                .build();
        GameMemory saved = gameMemoryRepository.save(memory);
        historyService.record(HistoryEventType.MEMORY_ADDED, game.getId(), game.getTitle(),
                "Nova memória em " + game.getTitle() + ": \"" + saved.getTitle() + "\".");
        return GameMemoryResponse.from(saved);
    }

    public GameMemoryResponse update(Long id, GameMemoryRequest request) {
        GameMemory memory = getOrThrow(id);
        memory.setTitle(request.getTitle());
        memory.setDescription(request.getDescription());
        memory.setMemoryDate(request.getMemoryDate());
        memory.setType(request.getType());
        return GameMemoryResponse.from(gameMemoryRepository.save(memory));
    }

    public void delete(Long id) {
        getOrThrow(id);
        gameMemoryRepository.deleteById(id);
    }

    private GameMemory getOrThrow(Long id) {
        return gameMemoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Memória não encontrada: " + id));
    }

    private Game getGameOrThrow(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new NotFoundException("Game não encontrado: " + gameId));
    }
}
