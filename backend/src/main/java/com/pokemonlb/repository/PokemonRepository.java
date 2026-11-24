package com.pokemonlb.repository;

import com.pokemonlb.model.entity.Pokemon;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/** Paged, filtered lists go through findAll(Specification, Pageable) with {@link PokemonSpecifications}. */
public interface PokemonRepository extends JpaRepository<Pokemon, Integer>, JpaSpecificationExecutor<Pokemon> {

    Optional<Pokemon> findByNameIgnoreCase(String name);

    /** All Pokémon in a game's Pokédex, by National Pokédex number. */
    List<Pokemon> findByGames_IdOrderByIdAsc(Integer gameId);

    /** All Pokémon that have a type in either slot, by National Pokédex number. */
    List<Pokemon> findByTypes_Type_IdOrderByIdAsc(Integer typeId);
}
