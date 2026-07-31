package com.fabio.gamememories.dto.saga;

import com.fabio.gamememories.enums.GalleryItemType;
import com.fabio.gamememories.enums.MusicChangePolicy;
import lombok.Data;

import java.util.List;

@Data
public class SagaRequest {
    private String name;
    private List<String> gameSagaNames;
    private GalleryItemType heroImageType;
    private Long heroImageId;
    private GalleryItemType secondImageType;
    private Long secondImageId;
    private Long themeMusicId;
    private MusicChangePolicy musicChangePolicy;
    private List<SagaGalleryItemRequest> galleryItems;
}
