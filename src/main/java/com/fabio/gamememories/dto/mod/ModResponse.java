package com.fabio.gamememories.dto.mod;

import com.fabio.gamememories.entity.Mod;
import lombok.Data;

@Data
public class ModResponse {
    private Long id;
    private Long gameId;
    private String title;
    private String description;
    private String link;
    private Boolean active;
    private String notes;

    public static ModResponse from(Mod mod) {
        ModResponse response = new ModResponse();
        response.setId(mod.getId());
        response.setGameId(mod.getGame().getId());
        response.setTitle(mod.getTitle());
        response.setDescription(mod.getDescription());
        response.setLink(mod.getLink());
        response.setActive(mod.getActive());
        response.setNotes(mod.getNotes());
        return response;
    }
}
