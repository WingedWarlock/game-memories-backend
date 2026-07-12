package com.fabio.gamememories.dto.achievement;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AchievementRequest {
    private String title;
    private String description;
    private Boolean unlocked;
    private LocalDate unlockedDate;
}
