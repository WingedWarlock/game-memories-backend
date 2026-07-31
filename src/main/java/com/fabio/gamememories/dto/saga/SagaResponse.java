package com.fabio.gamememories.dto.saga;

import com.fabio.gamememories.entity.Saga;
import com.fabio.gamememories.enums.GalleryItemType;
import com.fabio.gamememories.enums.MusicChangePolicy;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SagaResponse {
    private Long id;
    private String name;
    private List<String> gameSagaNames;
    private GalleryItemType heroImageType;
    private Long heroImageId;
    private GalleryItemType secondImageType;
    private Long secondImageId;
    private Long themeMusicId;
    private MusicChangePolicy musicChangePolicy;
    private List<SagaGalleryItemResponse> galleryItems;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static SagaResponse from(Saga saga) {
        SagaResponse response = new SagaResponse();
        response.setId(saga.getId());
        response.setName(saga.getName());
        response.setGameSagaNames(saga.getGameSagaNames());
        response.setHeroImageType(saga.getHeroImageType());
        response.setHeroImageId(saga.getHeroImageId());
        response.setSecondImageType(saga.getSecondImageType());
        response.setSecondImageId(saga.getSecondImageId());
        response.setThemeMusicId(saga.getThemeMusicId());
        response.setMusicChangePolicy(saga.getMusicChangePolicy());
        response.setGalleryItems(saga.getGalleryItems().stream().map(SagaGalleryItemResponse::from).toList());
        response.setCreatedAt(saga.getCreatedAt());
        response.setUpdatedAt(saga.getUpdatedAt());
        return response;
    }
}
