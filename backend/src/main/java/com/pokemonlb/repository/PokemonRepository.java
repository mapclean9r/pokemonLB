package com.pokemonlb.repository;

import com.pokemonlb.model.entity.Pokemon;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {

    List<Pokemon> findAllByOrderByIdAsc();

    Optional<Pokemon> findByNameIgnoreCase(String name);

    List<Pokemon> findByGenerationOrderByIdAsc(Short generation);

    /** All Pokémon in a game's Pokédex, by National Pokédex number. */
    List<Pokemon> findByGames_IdOrderByIdAsc(Integer gameId);

    List<Pokemon> findByGenerationAndGames_IdOrderByIdAsc(Short generation, Integer gameId);

    /** All Pokémon that have a type in either slot, by National Pokédex number. */
    List<Pokemon> findByTypes_Type_IdOrderByIdAsc(Integer typeId);
}
