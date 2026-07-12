package com.fabio.gamememories.dto.achievement;

import com.fabio.gamememories.entity.Achievement;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AchievementResponse {
    private Long id;
    private Long gameId;
    private String title;
    private String description;
    private Boolean unlocked;
    private LocalDate unlockedDate;

    public static AchievementResponse from(Achievement achievement) {
        AchievementResponse response = new AchievementResponse();
        response.setId(achievement.getId());
        response.setGameId(achievement.getGame().getId());
        response.setTitle(achievement.getTitle());
        response.setDescription(achievement.getDescription());
        response.setUnlocked(achievement.getUnlocked());
        response.setUnlockedDate(achievement.getUnlockedDate());
        return response;
    }
}
