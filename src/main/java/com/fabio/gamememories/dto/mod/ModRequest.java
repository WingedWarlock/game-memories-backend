package com.fabio.gamememories.dto.mod;

import lombok.Data;

@Data
public class ModRequest {
    private String title;
    private String description;
    private String link;
    private Boolean active;
    private String notes;
}
