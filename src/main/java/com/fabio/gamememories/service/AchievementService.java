package com.fabio.gamememories.service;

import com.fabio.gamememories.dto.achievement.AchievementRequest;
import com.fabio.gamememories.dto.achievement.AchievementResponse;
import com.fabio.gamememories.entity.Achievement;
import com.fabio.gamememories.entity.Game;
import com.fabio.gamememories.enums.HistoryEventType;
import com.fabio.gamememories.exception.NotFoundException;
import com.fabio.gamememories.repository.AchievementRepository;
import com.fabio.gamememories.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final GameRepository gameRepository;
    private final HistoryService historyService;

    public List<AchievementResponse> findByGame(Long gameId) {
        getGameOrThrow(gameId);
        return achievementRepository.findByGameId(gameId).stream()
                .map(AchievementResponse::from)
                .toList();
    }

    public AchievementResponse findById(Long id) {
        return AchievementResponse.from(getOrThrow(id));
    }

    public AchievementResponse create(Long gameId, AchievementRequest request) {
        Game game = getGameOrThrow(gameId);
        Achievement achievement = Achievement.builder()
                .game(game)
                .title(request.getTitle())
                .description(request.getDescription())
                .unlocked(request.getUnlocked())
                .unlockedDate(request.getUnlockedDate())
                .build();
        Achievement saved = achievementRepository.save(achievement);
        if (Boolean.TRUE.equals(saved.getUnlocked())) {
            historyService.record(HistoryEventType.ACHIEVEMENT_UNLOCKED, game.getId(), game.getTitle(),
                    "Conquista desbloqueada em " + game.getTitle() + ": \"" + saved.getTitle() + "\".");
        }
        return AchievementResponse.from(saved);
    }

    public AchievementResponse update(Long id, AchievementRequest request) {
        Achievement achievement = getOrThrow(id);
        boolean wasUnlocked = Boolean.TRUE.equals(achievement.getUnlocked());

        achievement.setTitle(request.getTitle());
        achievement.setDescription(request.getDescription());
        achievement.setUnlocked(request.getUnlocked());
        achievement.setUnlockedDate(request.getUnlockedDate());
        Achievement saved = achievementRepository.save(achievement);

        if (Boolean.TRUE.equals(saved.getUnlocked()) && !wasUnlocked) {
            Game game = saved.getGame();
            historyService.record(HistoryEventType.ACHIEVEMENT_UNLOCKED, game.getId(), game.getTitle(),
                    "Conquista desbloqueada em " + game.getTitle() + ": \"" + saved.getTitle() + "\".");
        }
        return AchievementResponse.from(saved);
    }

    public void delete(Long id) {
        getOrThrow(id);
        achievementRepository.deleteById(id);
    }

    private Achievement getOrThrow(Long id) {
        return achievementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Conquista não encontrada: " + id));
    }

    private Game getGameOrThrow(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new NotFoundException("Game não encontrado: " + gameId));
    }
}
