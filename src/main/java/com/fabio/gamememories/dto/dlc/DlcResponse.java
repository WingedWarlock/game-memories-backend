package com.fabio.gamememories.dto.dlc;

import com.fabio.gamememories.entity.Dlc;
import lombok.Data;

@Data
public class DlcResponse {
    private Long id;
    private Long gameId;
    private String title;
    private String description;
    private Boolean completed;
    private String notes;

    public static DlcResponse from(Dlc dlc) {
        DlcResponse response = new DlcResponse();
        response.setId(dlc.getId());
        response.setGameId(dlc.getGame().getId());
        response.setTitle(dlc.getTitle());
        response.setDescription(dlc.getDescription());
        response.setCompleted(dlc.getCompleted());
        response.setNotes(dlc.getNotes());
        return response;
    }
}
