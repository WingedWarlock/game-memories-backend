package com.fabio.gamememories.service;

import com.fabio.gamememories.dto.saga.SagaGalleryItemRequest;
import com.fabio.gamememories.dto.saga.SagaRequest;
import com.fabio.gamememories.dto.saga.SagaResponse;
import com.fabio.gamememories.entity.Saga;
import com.fabio.gamememories.entity.SagaGalleryItem;
import com.fabio.gamememories.enums.HistoryEventType;
import com.fabio.gamememories.enums.MusicChangePolicy;
import com.fabio.gamememories.exception.NotFoundException;
import com.fabio.gamememories.repository.SagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SagaService {

    private final SagaRepository sagaRepository;
    private final HistoryService historyService;

    public List<SagaResponse> findAll() {
        return sagaRepository.findAll().stream()
                .map(SagaResponse::from)
                .toList();
    }

    public SagaResponse findById(Long id) {
        return SagaResponse.from(getOrThrow(id));
    }

    public SagaResponse create(SagaRequest request) {
        Saga saga = Saga.builder()
                .name(request.getName())
                .gameSagaNames(request.getGameSagaNames() != null ? request.getGameSagaNames() : new ArrayList<>())
                .heroImageType(request.getHeroImageType())
                .heroImageId(request.getHeroImageId())
                .secondImageType(request.getSecondImageType())
                .secondImageId(request.getSecondImageId())
                .themeMusicId(request.getThemeMusicId())
                .musicChangePolicy(request.getMusicChangePolicy() != null ? request.getMusicChangePolicy() : MusicChangePolicy.SAME_THROUGHOUT)
                .build();
        applyGalleryItems(saga, request.getGalleryItems());
        Saga saved = sagaRepository.save(saga);
        historyService.record(HistoryEventType.SAGA_CREATED, null, null,
                "Constelação \"" + saved.getName() + "\" criada.");
        return SagaResponse.from(saved);
    }

    public SagaResponse update(Long id, SagaRequest request) {
        Saga saga = getOrThrow(id);
        saga.setName(request.getName());
        saga.setGameSagaNames(request.getGameSagaNames() != null ? request.getGameSagaNames() : new ArrayList<>());
        saga.setHeroImageType(request.getHeroImageType());
        saga.setHeroImageId(request.getHeroImageId());
        saga.setSecondImageType(request.getSecondImageType());
        saga.setSecondImageId(request.getSecondImageId());
        saga.setThemeMusicId(request.getThemeMusicId());
        saga.setMusicChangePolicy(request.getMusicChangePolicy() != null ? request.getMusicChangePolicy() : MusicChangePolicy.SAME_THROUGHOUT);
        applyGalleryItems(saga, request.getGalleryItems());
        return SagaResponse.from(sagaRepository.save(saga));
    }

    public void delete(Long id) {
        getOrThrow(id);
        sagaRepository.deleteById(id);
    }

    private void applyGalleryItems(Saga saga, List<SagaGalleryItemRequest> items) {
        saga.getGalleryItems().clear();
        if (items != null) {
            for (SagaGalleryItemRequest item : items) {
                saga.getGalleryItems().add(SagaGalleryItem.builder()
                        .saga(saga)
                        .itemType(item.getItemType())
                        .itemId(item.getItemId())
                        .build());
            }
        }
    }

    private Saga getOrThrow(Long id) {
        return sagaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Constelação não encontrada: " + id));
    }
}
