package com.fabio.gamememories.service;

import com.fabio.gamememories.dto.run.RunRequest;
import com.fabio.gamememories.dto.run.RunResponse;
import com.fabio.gamememories.entity.Game;
import com.fabio.gamememories.entity.Run;
import com.fabio.gamememories.exception.NotFoundException;
import com.fabio.gamememories.repository.GameRepository;
import com.fabio.gamememories.repository.RunRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RunService {

    private final RunRepository runRepository;
    private final GameRepository gameRepository;

    public List<RunResponse> findByGame(Long gameId) {
        getGameOrThrow(gameId);
        return runRepository.findByGameId(gameId).stream()
                .map(RunResponse::from)
                .toList();
    }

    public RunResponse findById(Long id) {
        return RunResponse.from(getOrThrow(id));
    }

    public RunResponse create(Long gameId, RunRequest request) {
        Game game = getGameOrThrow(gameId);
        Run run = Run.builder()
                .game(game)
                .runName(request.getRunName())
                .difficulty(request.getDifficulty())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .completionPercentage(request.getCompletionPercentage())
                .status(request.getStatus())
                .favoriteRun(request.getFavoriteRun())
                .notes(request.getNotes())
                .build();
        return RunResponse.from(runRepository.save(run));
    }

    public RunResponse update(Long id, RunRequest request) {
        Run run = getOrThrow(id);
        run.setRunName(request.getRunName());
        run.setDifficulty(request.getDifficulty());
        run.setStartDate(request.getStartDate());
        run.setEndDate(request.getEndDate());
        run.setCompletionPercentage(request.getCompletionPercentage());
        run.setStatus(request.getStatus());
        run.setFavoriteRun(request.getFavoriteRun());
        run.setNotes(request.getNotes());
        return RunResponse.from(runRepository.save(run));
    }

    public void delete(Long id) {
        getOrThrow(id);
        runRepository.deleteById(id);
    }

    private Run getOrThrow(Long id) {
        return runRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Run não encontrada: " + id));
    }

    private Game getGameOrThrow(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new NotFoundException("Game não encontrado: " + gameId));
    }
}
