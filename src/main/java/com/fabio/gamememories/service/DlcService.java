package com.fabio.gamememories.service;

import com.fabio.gamememories.dto.dlc.DlcRequest;
import com.fabio.gamememories.dto.dlc.DlcResponse;
import com.fabio.gamememories.entity.Dlc;
import com.fabio.gamememories.entity.Game;
import com.fabio.gamememories.exception.NotFoundException;
import com.fabio.gamememories.repository.DlcRepository;
import com.fabio.gamememories.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DlcService {

    private final DlcRepository dlcRepository;
    private final GameRepository gameRepository;

    public List<DlcResponse> findByGame(Long gameId) {
        getGameOrThrow(gameId);
        return dlcRepository.findByGameId(gameId).stream()
                .map(DlcResponse::from)
                .toList();
    }

    public DlcResponse findById(Long id) {
        return DlcResponse.from(getOrThrow(id));
    }

    public DlcResponse create(Long gameId, DlcRequest request) {
        Game game = getGameOrThrow(gameId);
        Dlc dlc = Dlc.builder()
                .game(game)
                .title(request.getTitle())
                .description(request.getDescription())
                .completed(request.getCompleted())
                .notes(request.getNotes())
                .build();
        return DlcResponse.from(dlcRepository.save(dlc));
    }

    public DlcResponse update(Long id, DlcRequest request) {
        Dlc dlc = getOrThrow(id);
        dlc.setTitle(request.getTitle());
        dlc.setDescription(request.getDescription());
        dlc.setCompleted(request.getCompleted());
        dlc.setNotes(request.getNotes());
        return DlcResponse.from(dlcRepository.save(dlc));
    }

    public void delete(Long id) {
        getOrThrow(id);
        dlcRepository.deleteById(id);
    }

    private Dlc getOrThrow(Long id) {
        return dlcRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("DLC não encontrada: " + id));
    }

    private Game getGameOrThrow(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new NotFoundException("Game não encontrado: " + gameId));
    }
}
