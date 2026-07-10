package com.fabio.gamememories.service;

import com.fabio.gamememories.dto.game.GameRequest;
import com.fabio.gamememories.dto.game.GameResponse;
import com.fabio.gamememories.entity.Game;
import com.fabio.gamememories.exception.NotFoundException;
import com.fabio.gamememories.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public List<GameResponse> findAll() {
        return gameRepository.findAll().stream()
                .map(GameResponse::from)
                .toList();
    }

    public GameResponse findById(Long id) {
        return GameResponse.from(getOrThrow(id));
    }

    public GameResponse create(GameRequest request) {
        Game game = Game.builder()
                .title(request.getTitle())
                .saga(request.getSaga())
                .genre(request.getGenre())
                .platform(request.getPlatform())
                .description(request.getDescription())
                .notes(request.getNotes())
                .favorite(request.getFavorite())
                .status(request.getStatus())
                .build();
        return GameResponse.from(gameRepository.save(game));
    }

    public GameResponse update(Long id, GameRequest request) {
        Game game = getOrThrow(id);
        game.setTitle(request.getTitle());
        game.setSaga(request.getSaga());
        game.setGenre(request.getGenre());
        game.setPlatform(request.getPlatform());
        game.setDescription(request.getDescription());
        game.setNotes(request.getNotes());
        game.setFavorite(request.getFavorite());
        game.setStatus(request.getStatus());
        return GameResponse.from(gameRepository.save(game));
    }

    public void delete(Long id) {
        getOrThrow(id);
        gameRepository.deleteById(id);
    }

    private Game getOrThrow(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Game não encontrado: " + id));
    }
}
