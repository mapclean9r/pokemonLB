package com.pokemonlb.repository;

import com.pokemonlb.model.entity.PokemonEvolution;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonEvolutionRepository extends JpaRepository<PokemonEvolution, Integer> {

    /** What a Pokémon evolves from (every Pokémon has at most one pre-evolution). */
    Optional<PokemonEvolution> findByTo_Id(Integer pokemonId);

    /** Every step out of the given Pokémon, with both sides loaded. */
    @EntityGraph(attributePaths = {"from", "to"})
    List<PokemonEvolution> findByFrom_IdInOrderById(Collection<Integer> pokemonIds);
}
