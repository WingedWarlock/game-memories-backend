package com.fabio.gamememories.service;

import com.fabio.gamememories.dto.mod.ModRequest;
import com.fabio.gamememories.dto.mod.ModResponse;
import com.fabio.gamememories.entity.Game;
import com.fabio.gamememories.entity.Mod;
import com.fabio.gamememories.exception.NotFoundException;
import com.fabio.gamememories.repository.GameRepository;
import com.fabio.gamememories.repository.ModRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModService {

    private final ModRepository modRepository;
    private final GameRepository gameRepository;

    public List<ModResponse> findByGame(Long gameId) {
        getGameOrThrow(gameId);
        return modRepository.findByGameId(gameId).stream()
                .map(ModResponse::from)
                .toList();
    }

    public ModResponse findById(Long id) {
        return ModResponse.from(getOrThrow(id));
    }

    public ModResponse create(Long gameId, ModRequest request) {
        Game game = getGameOrThrow(gameId);
        Mod mod = Mod.builder()
                .game(game)
                .title(request.getTitle())
                .description(request.getDescription())
                .link(request.getLink())
                .active(request.getActive())
                .notes(request.getNotes())
                .build();
        return ModResponse.from(modRepository.save(mod));
    }

    public ModResponse update(Long id, ModRequest request) {
        Mod mod = getOrThrow(id);
        mod.setTitle(request.getTitle());
        mod.setDescription(request.getDescription());
        mod.setLink(request.getLink());
        mod.setActive(request.getActive());
        mod.setNotes(request.getNotes());
        return ModResponse.from(modRepository.save(mod));
    }

    public void delete(Long id) {
        getOrThrow(id);
        modRepository.deleteById(id);
    }

    private Mod getOrThrow(Long id) {
        return modRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Mod não encontrado: " + id));
    }

    private Game getGameOrThrow(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new NotFoundException("Game não encontrado: " + gameId));
    }
}
