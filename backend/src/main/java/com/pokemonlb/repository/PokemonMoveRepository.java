package com.pokemonlb.repository;

import com.pokemonlb.model.dto.MoveLevelByGenerationDto;
import com.pokemonlb.model.entity.Move;
import com.pokemonlb.model.entity.PokemonMove;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PokemonMoveRepository extends JpaRepository<PokemonMove, PokemonMove.Id> {

    /** A Pokémon's full learnset in one game, with move and method loaded. */
    @Query("""
            select pm from PokemonMove pm
            join fetch pm.move join fetch pm.learnMethod
            where pm.pokemon.id = :pokemonId and pm.game.id = :gameId
            order by pm.learnMethod.id, pm.id.level, pm.move.name""")
    List<PokemonMove> findLearnset(Integer pokemonId, Integer gameId);

    /** Moves a Pokémon can learn in a generation (any game of that generation, any method). */
    @Query("""
            select distinct m from PokemonMove pm join pm.move m
            where pm.pokemon.id = :pokemonId and pm.game.generation = :generation
            order by m.name""")
    List<Move> findMovesByGeneration(Integer pokemonId, Short generation);

    /** Level-up level of a move per generation (lowest and highest across that generation's games). */
    @Query("""
            select new com.pokemonlb.model.dto.MoveLevelByGenerationDto(
                pm.game.generation, min(pm.id.level), max(pm.id.level))
            from PokemonMove pm
            where pm.pokemon.id = :pokemonId and pm.move.id = :moveId and pm.learnMethod.id = 1
            group by pm.game.generation
            order by pm.game.generation""")
    List<MoveLevelByGenerationDto> findLevelsByGeneration(Integer pokemonId, Integer moveId);
}
