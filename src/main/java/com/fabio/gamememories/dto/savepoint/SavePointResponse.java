package com.fabio.gamememories.dto.savepoint;

import com.fabio.gamememories.entity.SavePoint;
import lombok.Data;

@Data
public class SavePointResponse {
    private Long id;
    private Long runId;
    private String slot;
    private String title;
    private String description;

    public static SavePointResponse from(SavePoint savePoint) {
        SavePointResponse response = new SavePointResponse();
        response.setId(savePoint.getId());
        response.setRunId(savePoint.getRun().getId());
        response.setSlot(savePoint.getSlot());
        response.setTitle(savePoint.getTitle());
        response.setDescription(savePoint.getDescription());
        return response;
    }
}
