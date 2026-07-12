package com.fabio.gamememories.service;

import com.fabio.gamememories.dto.game.GameRequest;
import com.fabio.gamememories.dto.game.GameResponse;
import com.fabio.gamememories.entity.Game;
import com.fabio.gamememories.enums.GameRating;
import com.fabio.gamememories.enums.GameStatus;
import com.fabio.gamememories.enums.HistoryEventType;
import com.fabio.gamememories.exception.NotFoundException;
import com.fabio.gamememories.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final HistoryService historyService;

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
                .rating(request.getRating())
                .myHundredPercent(request.getMyHundredPercent())
                .build();
        Game saved = gameRepository.save(game);
        historyService.record(HistoryEventType.GAME_ADDED, saved.getId(), saved.getTitle(),
                saved.getTitle() + " foi adicionado à biblioteca.");
        return GameResponse.from(saved);
    }

    public GameResponse update(Long id, GameRequest request) {
        Game game = getOrThrow(id);
        boolean wasFavorite = Boolean.TRUE.equals(game.getFavorite());
        boolean wasHundredPercent = Boolean.TRUE.equals(game.getMyHundredPercent());
        GameRating previousRating = game.getRating();
        GameStatus previousStatus = game.getStatus();

        game.setTitle(request.getTitle());
        game.setSaga(request.getSaga());
        game.setGenre(request.getGenre());
        game.setPlatform(request.getPlatform());
        game.setDescription(request.getDescription());
        game.setNotes(request.getNotes());
        game.setFavorite(request.getFavorite());
        game.setStatus(request.getStatus());
        game.setRating(request.getRating());
        game.setMyHundredPercent(request.getMyHundredPercent());
        Game saved = gameRepository.save(game);

        boolean isFavorite = Boolean.TRUE.equals(saved.getFavorite());
        boolean isHundredPercent = Boolean.TRUE.equals(saved.getMyHundredPercent());
        if (isFavorite && !wasFavorite) {
            historyService.record(HistoryEventType.GAME_FAVORITED, saved.getId(), saved.getTitle(),
                    saved.getTitle() + " foi marcado como favorito.");
        } else if (!isFavorite && wasFavorite) {
            historyService.record(HistoryEventType.GAME_UNFAVORITED, saved.getId(), saved.getTitle(),
                    saved.getTitle() + " deixou de ser favorito.");
        }
        if (isHundredPercent && !wasHundredPercent) {
            historyService.record(HistoryEventType.GAME_HUNDRED_PERCENT, saved.getId(), saved.getTitle(),
                    saved.getTitle() + " alcançou o Meu 100%! 👑");
        }
        if (saved.getRating() != null && saved.getRating() != previousRating) {
            historyService.record(HistoryEventType.GAME_RATING_CHANGED, saved.getId(), saved.getTitle(),
                    "Você avaliou " + saved.getTitle() + " como " + ratingLabel(saved.getRating()) + ".");
        }
        if (saved.getStatus() != previousStatus) {
            historyService.record(HistoryEventType.GAME_STATUS_CHANGED, saved.getId(), saved.getTitle(),
                    saved.getTitle() + " mudou de status para " + statusLabel(saved.getStatus()) + ".");
        }

        return GameResponse.from(saved);
    }

    public void delete(Long id) {
        Game game = getOrThrow(id);
        String title = game.getTitle();
        gameRepository.deleteById(id);
        historyService.record(HistoryEventType.GAME_REMOVED, null, title, title + " foi removido da biblioteca.");
    }

    private Game getOrThrow(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Game não encontrado: " + id));
    }

    private String statusLabel(GameStatus status) {
        return switch (status) {
            case NOT_STARTED -> "Não iniciado";
            case PLAYING -> "Jogando";
            case PAUSED -> "Pausado";
            case COMPLETED -> "Concluído";
        };
    }

    private String ratingLabel(GameRating rating) {
        return switch (rating) {
            case LENDARIO -> "Lendário";
            case MEMORAVEL -> "Memorável";
            case MUITO_BOM -> "Muito bom";
            case BOM -> "Bom";
            case NORMAL -> "Normal";
            case NAO_GOSTEI_MUITO -> "Não gostei muito";
            case RUIM -> "Ruim";
        };
    }
}
