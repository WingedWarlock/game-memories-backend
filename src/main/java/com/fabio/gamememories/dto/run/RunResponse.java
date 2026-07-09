package com.fabio.gamememories.dto.run;

import com.fabio.gamememories.entity.Run;
import com.fabio.gamememories.enums.RunStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RunResponse {
    private Long id;
    private Long gameId;
    private String runName;
    private String difficulty;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double completionPercentage;
    private RunStatus status;
    private Boolean favoriteRun;
    private String notes;

    public static RunResponse from(Run run) {
        RunResponse response = new RunResponse();
        response.setId(run.getId());
        response.setGameId(run.getGame().getId());
        response.setRunName(run.getRunName());
        response.setDifficulty(run.getDifficulty());
        response.setStartDate(run.getStartDate());
        response.setEndDate(run.getEndDate());
        response.setCompletionPercentage(run.getCompletionPercentage());
        response.setStatus(run.getStatus());
        response.setFavoriteRun(run.getFavoriteRun());
        response.setNotes(run.getNotes());
        return response;
    }
}
