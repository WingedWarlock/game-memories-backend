package com.fabio.gamememories.dto.dlc;

import lombok.Data;

@Data
public class DlcRequest {
    private String title;
    private String description;
    private Boolean completed;
    private String notes;
}
