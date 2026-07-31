package com.fabio.gamememories.dto.saga;

import com.fabio.gamememories.enums.GalleryItemType;
import lombok.Data;

@Data
public class SagaGalleryItemRequest {
    private GalleryItemType itemType;
    private Long itemId;
}
