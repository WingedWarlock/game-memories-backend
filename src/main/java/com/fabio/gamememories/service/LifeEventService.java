package com.fabio.gamememories.service;

import com.fabio.gamememories.dto.lifeevent.LifeEventRequest;
import com.fabio.gamememories.dto.lifeevent.LifeEventResponse;
import com.fabio.gamememories.entity.LifeEvent;
import com.fabio.gamememories.enums.HistoryEventType;
import com.fabio.gamememories.exception.NotFoundException;
import com.fabio.gamememories.repository.LifeEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LifeEventService {

    private final LifeEventRepository lifeEventRepository;
    private final HistoryService historyService;

    public List<LifeEventResponse> findAll() {
        return lifeEventRepository.findAll().stream()
                .map(LifeEventResponse::from)
                .toList();
    }

    public LifeEventResponse findById(Long id) {
        return LifeEventResponse.from(getOrThrow(id));
    }

    public LifeEventResponse create(LifeEventRequest request) {
        LifeEvent lifeEvent = LifeEvent.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .date(request.getDate())
                .category(request.getCategory())
                .build();
        LifeEvent saved = lifeEventRepository.save(lifeEvent);
        historyService.record(HistoryEventType.LIFE_EVENT_ADDED, null, null,
                "Novo momento de vida: \"" + saved.getTitle() + "\".");
        return LifeEventResponse.from(saved);
    }

    public LifeEventResponse update(Long id, LifeEventRequest request) {
        LifeEvent lifeEvent = getOrThrow(id);
        lifeEvent.setTitle(request.getTitle());
        lifeEvent.setDescription(request.getDescription());
        lifeEvent.setDate(request.getDate());
        lifeEvent.setCategory(request.getCategory());
        return LifeEventResponse.from(lifeEventRepository.save(lifeEvent));
    }

    public void delete(Long id) {
        getOrThrow(id);
        lifeEventRepository.deleteById(id);
    }

    private LifeEvent getOrThrow(Long id) {
        return lifeEventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Momento de vida não encontrado: " + id));
    }
}
