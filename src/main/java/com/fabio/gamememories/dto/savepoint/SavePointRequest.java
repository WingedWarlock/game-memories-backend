package com.fabio.gamememories.dto.savepoint;

import lombok.Data;

@Data
public class SavePointRequest {
    private String slot;
    private String title;
    private String description;
}
