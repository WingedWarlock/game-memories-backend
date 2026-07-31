package com.fabio.gamememories.dto.saga;

import com.fabio.gamememories.entity.SagaGalleryItem;
import com.fabio.gamememories.enums.GalleryItemType;
import lombok.Data;

@Data
public class SagaGalleryItemResponse {
    private Long id;
    private GalleryItemType itemType;
    private Long itemId;

    public static SagaGalleryItemResponse from(SagaGalleryItem item) {
        SagaGalleryItemResponse response = new SagaGalleryItemResponse();
        response.setId(item.getId());
        response.setItemType(item.getItemType());
        response.setItemId(item.getItemId());
        return response;
    }
}
