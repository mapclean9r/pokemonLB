package com.pokemonlb.service;

import com.pokemonlb.exception.NotFoundException;
import com.pokemonlb.model.dto.EvolutionDto;
import com.pokemonlb.model.dto.GameDto;
import com.pokemonlb.model.dto.LearnsetEntryDto;
import com.pokemonlb.model.dto.MoveLevelByGenerationDto;
import com.pokemonlb.model.dto.PokemonDetailDto;
import com.pokemonlb.model.dto.PokemonStatsDto;
import com.pokemonlb.model.dto.PokemonSummaryDto;
import com.pokemonlb.model.entity.Pokemon;
import com.pokemonlb.model.entity.PokemonEvolution;
import com.pokemonlb.repository.GameRepository;
import com.pokemonlb.repository.MoveRepository;
import com.pokemonlb.repository.PokemonEvolutionRepository;
import com.pokemonlb.repository.PokemonMoveRepository;
import com.pokemonlb.repository.PokemonRepository;
import com.pokemonlb.repository.PokemonStatsRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PokemonService {

    private final PokemonRepository pokemonRepository;
    private final PokemonStatsRepository statsRepository;
    private final GameRepository gameRepository;
    private final MoveRepository moveRepository;
    private final PokemonMoveRepository pokemonMoveRepository;
    private final PokemonEvolutionRepository evolutionRepository;

    /** All Pokémon, optionally only those from a generation and/or in a game's Pokédex. */
    public List<PokemonSummaryDto> findAll(Short generation, Integer gameId) {
        List<Pokemon> pokemon;
        if (generation != null && gameId != null) {
            pokemon = pokemonRepository.findByGenerationAndGames_IdOrderByIdAsc(generation, gameId);
        } else if (generation != null) {
            pokemon = pokemonRepository.findByGenerationOrderByIdAsc(generation);
        } else if (gameId != null) {
            pokemon = pokemonRepository.findByGames_IdOrderByIdAsc(gameId);
        } else {
            pokemon = pokemonRepository.findAllByOrderByIdAsc();
        }
        return pokemon.stream().map(PokemonSummaryDto::from).toList();
    }

    public PokemonDetailDto findById(Integer id) {
        Pokemon pokemon = getPokemon(id);
        PokemonStatsDto stats = statsRepository.findById(id).map(PokemonStatsDto::from).orElse(null);
        return PokemonDetailDto.from(pokemon, stats);
    }

    public List<GameDto> findGames(Integer id) {
        getPokemon(id);
        return gameRepository.findByPokemonId(id).stream().map(GameDto::from).toList();
    }

    public List<LearnsetEntryDto> findLearnset(Integer id, Integer gameId) {
        getPokemon(id);
        if (!gameRepository.existsById(gameId)) {
            throw new NotFoundException("Game", gameId);
        }
        return pokemonMoveRepository.findLearnset(id, gameId).stream().map(LearnsetEntryDto::from).toList();
    }

    public List<MoveLevelByGenerationDto> findMoveLevels(Integer id, Integer moveId) {
        getPokemon(id);
        if (!moveRepository.existsById(moveId)) {
            throw new NotFoundException("Move", moveId);
        }
        return pokemonMoveRepository.findLevelsByGeneration(id, moveId);
    }

    /**
     * The Pokémon's whole evolution family, first stage first: e.g. for Charmeleon,
     * Charmander -> Charmeleon (level 16) and Charmeleon -> Charizard (level 36).
     * Empty for Pokémon that neither evolve nor evolve from anything.
     */
    public List<EvolutionDto> findEvolutions(Integer id) {
        getPokemon(id);

        Integer firstStage = id;
        Optional<PokemonEvolution> preEvolution;
        while ((preEvolution = evolutionRepository.findByTo_Id(firstStage)).isPresent()) {
            firstStage = preEvolution.get().getFrom().getId();
        }

        List<EvolutionDto> family = new ArrayList<>();
        List<Integer> stage = List.of(firstStage);
        while (!stage.isEmpty()) {
            List<PokemonEvolution> steps = evolutionRepository.findByFrom_IdInOrderById(stage);
            steps.forEach(step -> family.add(EvolutionDto.from(step)));
            stage = steps.stream().map(step -> step.getTo().getId()).toList();
        }
        return family;
    }

    private Pokemon getPokemon(Integer id) {
        return pokemonRepository.findById(id).orElseThrow(() -> new NotFoundException("Pokémon", id));
    }
}
