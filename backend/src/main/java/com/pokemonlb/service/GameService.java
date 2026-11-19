package com.pokemonlb.service;

import com.pokemonlb.exception.NotFoundException;
import com.pokemonlb.model.dto.GameDto;
import com.pokemonlb.model.dto.ItemSummaryDto;
import com.pokemonlb.model.dto.MoveSummaryDto;
import com.pokemonlb.model.dto.PokemonSummaryDto;
import com.pokemonlb.repository.GameRepository;
import com.pokemonlb.repository.ItemGameAvailabilityRepository;
import com.pokemonlb.repository.MoveGameAvailabilityRepository;
import com.pokemonlb.repository.PokemonRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameService {

    private final GameRepository gameRepository;
    private final PokemonRepository pokemonRepository;
    private final MoveGameAvailabilityRepository moveAvailabilityRepository;
    private final ItemGameAvailabilityRepository itemAvailabilityRepository;

    /** All games in release order. */
    public List<GameDto> findAll() {
        return gameRepository.findAllByOrderByReleaseOrderAsc().stream().map(GameDto::from).toList();
    }

    public GameDto findById(Integer id) {
        return gameRepository.findById(id).map(GameDto::from).orElseThrow(() -> new NotFoundException("Game", id));
    }

    /** Pokémon in the game's Pokédex. */
    public List<PokemonSummaryDto> findPokemon(Integer id) {
        requireGame(id);
        return pokemonRepository.findByGames_IdOrderByIdAsc(id).stream().map(PokemonSummaryDto::from).toList();
    }

    /** Moves that exist in the game. */
    public List<MoveSummaryDto> findMoves(Integer id) {
        requireGame(id);
        return moveAvailabilityRepository.findByGameIdOrderByMoveName(id).stream().map(MoveSummaryDto::from).toList();
    }

    /** Items that exist in the game (incomplete for games after Sword / Shield). */
    public List<ItemSummaryDto> findItems(Integer id) {
        requireGame(id);
        return itemAvailabilityRepository.findByGameIdOrderByItemName(id).stream()
                .map(i -> new ItemSummaryDto(i.getItemId(), i.getItemName(), i.getCategory()))
                .toList();
    }

    private void requireGame(Integer id) {
        if (!gameRepository.existsById(id)) {
            throw new NotFoundException("Game", id);
        }
    }
}
