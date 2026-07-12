package com.fabio.gamememories.dto.lifeevent;

import com.fabio.gamememories.enums.LifeEventCategory;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LifeEventRequest {
    private String title;
    private String description;
    private LocalDate date;
    private LifeEventCategory category;
}
