package com.fabio.gamememories.service;

import com.fabio.gamememories.dto.lifeevent.LifeEventResponse;
import com.fabio.gamememories.dto.stats.RetrospectiveResponse;
import com.fabio.gamememories.dto.stats.StatsResponse;
import com.fabio.gamememories.entity.*;
import com.fabio.gamememories.enums.GameRating;
import com.fabio.gamememories.enums.GameStatus;
import com.fabio.gamememories.enums.RunStatus;
import com.fabio.gamememories.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatsService {

    private final GameRepository gameRepository;
    private final RunRepository runRepository;
    private final GameMemoryRepository gameMemoryRepository;
    private final GameScreenshotRepository gameScreenshotRepository;
    private final GameMusicRepository gameMusicRepository;
    private final AchievementRepository achievementRepository;
    private final DlcRepository dlcRepository;
    private final ModRepository modRepository;
    private final SavePointRepository savePointRepository;
    private final LifeEventRepository lifeEventRepository;

    private static final List<GameRating> RATING_RANK = List.of(
            GameRating.RUIM, GameRating.NAO_GOSTEI_MUITO, GameRating.NORMAL, GameRating.BOM,
            GameRating.MUITO_BOM, GameRating.MEMORAVEL, GameRating.LENDARIO);

    public StatsResponse getStats() {
        List<Game> games = gameRepository.findAll();
        List<Run> runs = runRepository.findAll();
        List<GameMemory> memories = gameMemoryRepository.findAll();
        List<GameScreenshot> screenshots = gameScreenshotRepository.findAll();
        List<GameMusic> music = gameMusicRepository.findAll();
        List<Achievement> achievements = achievementRepository.findAll();
        List<Dlc> dlcs = dlcRepository.findAll();
        List<Mod> mods = modRepository.findAll();
        List<SavePoint> savePoints = savePointRepository.findAll();

        Map<Long, Game> gameById = games.stream().collect(Collectors.toMap(Game::getId, g -> g));

        StatsResponse.Library library = new StatsResponse.Library();
        library.setTotalGames(games.size());
        library.setFavoriteGames(games.stream().filter(g -> Boolean.TRUE.equals(g.getFavorite())).count());
        library.setCompletedGames(games.stream().filter(g -> g.getStatus() == GameStatus.COMPLETED).count());
        library.setPlayingGames(games.stream().filter(g -> g.getStatus() == GameStatus.PLAYING).count());
        library.setPausedGames(games.stream().filter(g -> g.getStatus() == GameStatus.PAUSED).count());
        library.setNotStartedGames(games.stream().filter(g -> g.getStatus() == GameStatus.NOT_STARTED).count());
        library.setHundredPercentGames(games.stream().filter(g -> Boolean.TRUE.equals(g.getMyHundredPercent())).count());
        library.setTotalRuns(runs.size());
        library.setTotalMemories(memories.size());
        library.setTotalSavePoints(savePoints.size());
        library.setTotalScreenshots(screenshots.size());
        library.setTotalMusic(music.size());
        library.setTotalMods(mods.size());
        library.setTotalDlcs(dlcs.size());
        library.setTotalAchievements(achievements.size());
        library.setUnlockedAchievements(achievements.stream().filter(a -> Boolean.TRUE.equals(a.getUnlocked())).count());

        StatsResponse response = new StatsResponse();
        response.setLibrary(library);
        response.setBySaga(countBy(games, Game::getSaga));
        response.setByPlatform(countBy(games, Game::getPlatform));
        response.setByGenre(countBy(games, Game::getGenre));
        response.setByStatus(countBy(games, g -> statusLabel(g.getStatus())));
        response.setByRating(countBy(games.stream().filter(g -> g.getRating() != null).toList(), g -> ratingLabel(g.getRating())));

        StatsResponse.TimelineHighlights timeline = new StatsResponse.TimelineHighlights();
        timeline.setYearWithMostGamesStarted(topYear(runs.stream()
                .filter(r -> r.getStartDate() != null)
                .collect(Collectors.groupingBy(r -> r.getStartDate().getYear(), Collectors.mapping(r -> r.getGame().getId(), Collectors.toSet())))
                .entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> (long) e.getValue().size()))));
        timeline.setYearWithMostGamesCompleted(topYear(runs.stream()
                .filter(r -> r.getStatus() == RunStatus.COMPLETED && r.getEndDate() != null)
                .collect(Collectors.groupingBy(r -> r.getEndDate().getYear(), Collectors.mapping(r -> r.getGame().getId(), Collectors.toSet())))
                .entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> (long) e.getValue().size()))));
        timeline.setYearWithMostMemories(topYear(memories.stream()
                .filter(m -> m.getMemoryDate() != null)
                .collect(Collectors.groupingBy(m -> m.getMemoryDate().getYear(), Collectors.counting()))));
        timeline.setYearWithMostRuns(topYear(runs.stream()
                .filter(r -> r.getStartDate() != null)
                .collect(Collectors.groupingBy(r -> r.getStartDate().getYear(), Collectors.counting()))));
        response.setTimelineHighlights(timeline);

        StatsResponse.Highlights highlights = new StatsResponse.Highlights();
        highlights.setMostPlayedSaga(topName(response.getBySaga()));
        highlights.setFavoriteGenre(topName(response.getByGenre()));
        highlights.setMostUsedPlatform(topName(response.getByPlatform()));
        games.stream().filter(g -> g.getCreatedAt() != null).min(Comparator.comparing(Game::getCreatedAt))
                .ifPresent(g -> highlights.setFirstGameRegistered(new StatsResponse.GameRef(g.getId(), g.getTitle())));
        games.stream().filter(g -> g.getCreatedAt() != null).max(Comparator.comparing(Game::getCreatedAt))
                .ifPresent(g -> highlights.setLastGameRegistered(new StatsResponse.GameRef(g.getId(), g.getTitle())));
        topGameByCount(runs.stream().collect(Collectors.groupingBy(r -> r.getGame().getId(), Collectors.counting())), gameById)
                .ifPresent(highlights::setGameWithMostRuns);
        topGameByCount(memories.stream().collect(Collectors.groupingBy(m -> m.getGame().getId(), Collectors.counting())), gameById)
                .ifPresent(highlights::setGameWithMostMemories);
        topGameByCount(savePoints.stream().collect(Collectors.groupingBy(sp -> sp.getRun().getGame().getId(), Collectors.counting())), gameById)
                .ifPresent(highlights::setGameWithMostSavePoints);
        response.setHighlights(highlights);

        return response;
    }

    public List<Integer> getRetrospectiveYears() {
        Set<Integer> years = new TreeSet<>(Comparator.reverseOrder());
        runRepository.findAll().forEach(r -> {
            if (r.getStartDate() != null) years.add(r.getStartDate().getYear());
            if (r.getEndDate() != null) years.add(r.getEndDate().getYear());
        });
        gameMemoryRepository.findAll().forEach(m -> {
            if (m.getMemoryDate() != null) years.add(m.getMemoryDate().getYear());
        });
        lifeEventRepository.findAll().forEach(le -> {
            if (le.getDate() != null) years.add(le.getDate().getYear());
        });
        gameRepository.findAll().forEach(g -> {
            if (g.getCreatedAt() != null) years.add(g.getCreatedAt().getYear());
        });
        return new ArrayList<>(years);
    }

    public RetrospectiveResponse getRetrospective(int year) {
        List<Run> allRuns = runRepository.findAll();
        List<GameMemory> allMemories = gameMemoryRepository.findAll();
        List<SavePoint> allSavePoints = savePointRepository.findAll();
        List<GameScreenshot> allScreenshots = gameScreenshotRepository.findAll();
        List<GameMusic> allMusic = gameMusicRepository.findAll();
        List<LifeEvent> allLifeEvents = lifeEventRepository.findAll();

        List<Run> runsStartedThisYear = allRuns.stream()
                .filter(r -> r.getStartDate() != null && r.getStartDate().getYear() == year).toList();
        List<Run> runsCompletedThisYear = allRuns.stream()
                .filter(r -> r.getStatus() == RunStatus.COMPLETED && r.getEndDate() != null && r.getEndDate().getYear() == year).toList();
        List<Run> runsTouchedThisYear = allRuns.stream()
                .filter(r -> (r.getStartDate() != null && r.getStartDate().getYear() == year)
                        || (r.getEndDate() != null && r.getEndDate().getYear() == year))
                .toList();

        RetrospectiveResponse response = new RetrospectiveResponse();
        response.setYear(year);
        response.setGamesStarted(runsStartedThisYear.stream().map(r -> r.getGame().getId()).distinct().count());
        response.setGamesCompleted(runsCompletedThisYear.stream().map(r -> r.getGame().getId()).distinct().count());
        response.setHundredPercentCount(runsCompletedThisYear.stream()
                .map(Run::getGame).filter(g -> Boolean.TRUE.equals(g.getMyHundredPercent()))
                .map(Game::getId).distinct().count());

        Set<Game> gamesTouched = runsTouchedThisYear.stream().map(Run::getGame)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        gamesTouched.stream().filter(g -> Boolean.TRUE.equals(g.getFavorite())).findFirst()
                .ifPresent(g -> response.setFavoriteOfYear(new StatsResponse.GameRef(g.getId(), g.getTitle())));

        gamesTouched.stream().filter(g -> g.getRating() != null)
                .max(Comparator.comparing(g -> RATING_RANK.indexOf(g.getRating())))
                .ifPresent(g -> response.setBestRatingOfYear(new RetrospectiveResponse.GameRatingRef(g.getId(), g.getTitle(), g.getRating())));

        response.setTopSaga(topKeyByRunCount(runsTouchedThisYear, r -> r.getGame().getSaga()));
        response.setTopGenre(topKeyByRunCount(runsTouchedThisYear, r -> r.getGame().getGenre()));
        response.setTopPlatform(topKeyByRunCount(runsTouchedThisYear, r -> r.getGame().getPlatform()));

        response.setRunsCount(runsTouchedThisYear.size());
        response.setMemoriesCount(allMemories.stream().filter(m -> m.getMemoryDate() != null && m.getMemoryDate().getYear() == year).count());
        response.setSavePointsCount(allSavePoints.stream().filter(sp -> sp.getDate() != null && sp.getDate().getYear() == year).count());
        response.setScreenshotsCount(allScreenshots.stream().filter(s -> s.getCreatedAt() != null && s.getCreatedAt().getYear() == year).count());
        response.setMusicCount(allMusic.stream().filter(m -> m.getCreatedAt() != null && m.getCreatedAt().getYear() == year).count());

        runsStartedThisYear.stream().min(Comparator.comparing(Run::getStartDate))
                .ifPresent(r -> response.setFirstGameStarted(new RetrospectiveResponse.GameDateRef(r.getGame().getId(), r.getGame().getTitle(), r.getStartDate())));
        runsStartedThisYear.stream().max(Comparator.comparing(Run::getStartDate))
                .ifPresent(r -> response.setLastGameStarted(new RetrospectiveResponse.GameDateRef(r.getGame().getId(), r.getGame().getTitle(), r.getStartDate())));
        runsCompletedThisYear.stream().min(Comparator.comparing(Run::getEndDate))
                .ifPresent(r -> response.setFirstGameCompleted(new RetrospectiveResponse.GameDateRef(r.getGame().getId(), r.getGame().getTitle(), r.getEndDate())));
        runsCompletedThisYear.stream().max(Comparator.comparing(Run::getEndDate))
                .ifPresent(r -> response.setLastGameCompleted(new RetrospectiveResponse.GameDateRef(r.getGame().getId(), r.getGame().getTitle(), r.getEndDate())));

        List<Run> runsWithSpan = runsTouchedThisYear.stream()
                .filter(r -> r.getStartDate() != null && r.getEndDate() != null)
                .toList();
        runsWithSpan.stream().max(Comparator.comparing(r -> ChronoUnit.DAYS.between(r.getStartDate(), r.getEndDate())))
                .ifPresent(r -> response.setLongestRun(new RetrospectiveResponse.RunSpan(r.getGame().getTitle(), r.getRunName(),
                        ChronoUnit.DAYS.between(r.getStartDate(), r.getEndDate()))));
        runsWithSpan.stream().min(Comparator.comparing(r -> ChronoUnit.DAYS.between(r.getStartDate(), r.getEndDate())))
                .ifPresent(r -> response.setShortestRun(new RetrospectiveResponse.RunSpan(r.getGame().getTitle(), r.getRunName(),
                        ChronoUnit.DAYS.between(r.getStartDate(), r.getEndDate()))));

        Map<Long, Game> gamesTouchedById = gamesTouched.stream().collect(Collectors.toMap(Game::getId, g -> g));
        topGameByCount(runsTouchedThisYear.stream().collect(Collectors.groupingBy(r -> r.getGame().getId(), Collectors.counting())), gamesTouchedById)
                .ifPresent(response::setMostPlayedGameOfYear);

        response.setHighlightMemories(allMemories.stream()
                .filter(m -> m.getMemoryDate() != null && m.getMemoryDate().getYear() == year)
                .sorted(Comparator.comparing(GameMemory::getMemoryDate))
                .limit(6)
                .map(m -> new RetrospectiveResponse.MemoryHighlight(m.getGame().getId(), m.getGame().getTitle(), m.getTitle(), m.getDescription(), m.getMemoryDate()))
                .toList());

        response.setLifeEvents(allLifeEvents.stream()
                .filter(le -> le.getDate() != null && le.getDate().getYear() == year)
                .sorted(Comparator.comparing(LifeEvent::getDate))
                .map(LifeEventResponse::from)
                .toList());

        return response;
    }

    private List<StatsResponse.NameCount> countBy(List<Game> games, Function<Game, String> keyFn) {
        return games.stream()
                .map(keyFn)
                .filter(k -> k != null && !k.isBlank())
                .collect(Collectors.groupingBy(k -> k, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .map(e -> new StatsResponse.NameCount(e.getKey(), e.getValue()))
                .toList();
    }

    private String topName(List<StatsResponse.NameCount> list) {
        return list.isEmpty() ? null : list.get(0).getName();
    }

    private StatsResponse.YearCount topYear(Map<Integer, Long> counts) {
        return counts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(e -> new StatsResponse.YearCount(e.getKey(), e.getValue()))
                .orElse(null);
    }

    private Optional<StatsResponse.GameCountRef> topGameByCount(Map<Long, Long> counts, Map<Long, Game> gameById) {
        return counts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .flatMap(e -> Optional.ofNullable(gameById.get(e.getKey()))
                        .map(g -> new StatsResponse.GameCountRef(g.getId(), g.getTitle(), e.getValue())));
    }

    private String topKeyByRunCount(List<Run> runs, Function<Run, String> keyFn) {
        return runs.stream()
                .map(keyFn)
                .filter(k -> k != null && !k.isBlank())
                .collect(Collectors.groupingBy(k -> k, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
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
