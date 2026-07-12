package com.fabio.gamememories.dto.savepoint;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SavePointRequest {
    private String slot;
    private String title;
    private String description;
    private LocalDate date;
}
