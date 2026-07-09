package com.fabio.gamememories.dto.run;

import com.fabio.gamememories.enums.RunStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RunRequest {
    private String runName;
    private String difficulty;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double completionPercentage;
    private RunStatus status;
    private Boolean favoriteRun;
    private String notes;
}
