package com.fabio.gamememories.dto.stats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatsResponse {

    private Library library;
    private List<NameCount> bySaga;
    private List<NameCount> byPlatform;
    private List<NameCount> byGenre;
    private List<NameCount> byStatus;
    private List<NameCount> byRating;
    private TimelineHighlights timelineHighlights;
    private Highlights highlights;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Library {
        private long totalGames;
        private long favoriteGames;
        private long completedGames;
        private long playingGames;
        private long pausedGames;
        private long notStartedGames;
        private long hundredPercentGames;
        private long totalRuns;
        private long totalMemories;
        private long totalSavePoints;
        private long totalScreenshots;
        private long totalMusic;
        private long totalMods;
        private long totalDlcs;
        private long totalAchievements;
        private long unlockedAchievements;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NameCount {
        private String name;
        private long count;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TimelineHighlights {
        private YearCount yearWithMostGamesStarted;
        private YearCount yearWithMostGamesCompleted;
        private YearCount yearWithMostMemories;
        private YearCount yearWithMostRuns;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class YearCount {
        private int year;
        private long count;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Highlights {
        private String mostPlayedSaga;
        private String favoriteGenre;
        private String mostUsedPlatform;
        private GameRef firstGameRegistered;
        private GameRef lastGameRegistered;
        private GameCountRef gameWithMostRuns;
        private GameCountRef gameWithMostMemories;
        private GameCountRef gameWithMostSavePoints;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GameRef {
        private Long id;
        private String title;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GameCountRef {
        private Long id;
        private String title;
        private long count;
    }
}
