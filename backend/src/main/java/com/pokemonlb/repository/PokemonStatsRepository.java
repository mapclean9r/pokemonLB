package com.pokemonlb.repository;

import com.pokemonlb.model.entity.PokemonStats;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonStatsRepository extends JpaRepository<PokemonStats, Integer> {

    /** Top 10 by base stat total, with the Pokémon loaded in the same query. */
    @EntityGraph(attributePaths = "pokemon")
    List<PokemonStats> findTop10ByOrderByTotalDesc();
}
