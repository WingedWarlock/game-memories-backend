package com.fabio.gamememories.service;

import com.fabio.gamememories.dto.savepoint.SavePointRequest;
import com.fabio.gamememories.dto.savepoint.SavePointResponse;
import com.fabio.gamememories.entity.Run;
import com.fabio.gamememories.entity.SavePoint;
import com.fabio.gamememories.exception.NotFoundException;
import com.fabio.gamememories.repository.RunRepository;
import com.fabio.gamememories.repository.SavePointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SavePointService {

    private final SavePointRepository savePointRepository;
    private final RunRepository runRepository;

    public List<SavePointResponse> findByRun(Long runId) {
        getRunOrThrow(runId);
        return savePointRepository.findByRunId(runId).stream()
                .map(SavePointResponse::from)
                .toList();
    }

    public SavePointResponse findById(Long id) {
        return SavePointResponse.from(getOrThrow(id));
    }

    public SavePointResponse create(Long runId, SavePointRequest request) {
        Run run = getRunOrThrow(runId);
        SavePoint savePoint = SavePoint.builder()
                .run(run)
                .slot(request.getSlot())
                .title(request.getTitle())
                .description(request.getDescription())
                .date(request.getDate())
                .build();
        return SavePointResponse.from(savePointRepository.save(savePoint));
    }

    public SavePointResponse update(Long id, SavePointRequest request) {
        SavePoint savePoint = getOrThrow(id);
        savePoint.setSlot(request.getSlot());
        savePoint.setTitle(request.getTitle());
        savePoint.setDescription(request.getDescription());
        savePoint.setDate(request.getDate());
        return SavePointResponse.from(savePointRepository.save(savePoint));
    }

    public void delete(Long id) {
        getOrThrow(id);
        savePointRepository.deleteById(id);
    }

    private SavePoint getOrThrow(Long id) {
        return savePointRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("SavePoint não encontrado: " + id));
    }

    private Run getRunOrThrow(Long runId) {
        return runRepository.findById(runId)
                .orElseThrow(() -> new NotFoundException("Run não encontrada: " + runId));
    }
}
