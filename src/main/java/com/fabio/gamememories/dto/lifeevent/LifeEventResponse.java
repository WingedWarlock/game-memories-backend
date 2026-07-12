package com.fabio.gamememories.dto.lifeevent;

import com.fabio.gamememories.entity.LifeEvent;
import com.fabio.gamememories.enums.LifeEventCategory;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LifeEventResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDate date;
    private LifeEventCategory category;

    public static LifeEventResponse from(LifeEvent lifeEvent) {
        LifeEventResponse response = new LifeEventResponse();
        response.setId(lifeEvent.getId());
        response.setTitle(lifeEvent.getTitle());
        response.setDescription(lifeEvent.getDescription());
        response.setDate(lifeEvent.getDate());
        response.setCategory(lifeEvent.getCategory());
        return response;
    }
}
