package com.fabio.gamememories.dto.stats;

import com.fabio.gamememories.dto.lifeevent.LifeEventResponse;
import com.fabio.gamememories.enums.GameRating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrospectiveResponse {
    private int year;
    private long gamesStarted;
    private long gamesCompleted;
    private long hundredPercentCount;
    private StatsResponse.GameRef favoriteOfYear;
    private GameRatingRef bestRatingOfYear;
    private String topSaga;
    private String topGenre;
    private String topPlatform;
    private long runsCount;
    private long memoriesCount;
    private long savePointsCount;
    private long screenshotsCount;
    private long musicCount;
    private GameDateRef firstGameStarted;
    private GameDateRef lastGameStarted;
    private GameDateRef firstGameCompleted;
    private GameDateRef lastGameCompleted;
    private RunSpan longestRun;
    private RunSpan shortestRun;
    private StatsResponse.GameCountRef mostPlayedGameOfYear;
    private List<MemoryHighlight> highlightMemories;
    private List<LifeEventResponse> lifeEvents;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GameRatingRef {
        private Long id;
        private String title;
        private GameRating rating;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GameDateRef {
        private Long id;
        private String title;
        private LocalDate date;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RunSpan {
        private String gameTitle;
        private String runName;
        private long days;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemoryHighlight {
        private Long gameId;
        private String gameTitle;
        private String title;
        private String description;
        private LocalDate memoryDate;
    }
}
