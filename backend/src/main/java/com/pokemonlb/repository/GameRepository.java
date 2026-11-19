package com.pokemonlb.repository;

import com.pokemonlb.model.entity.Game;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GameRepository extends JpaRepository<Game, Integer> {

    List<Game> findAllByOrderByReleaseOrderAsc();

    /** Games whose Pokédex includes a Pokémon, oldest first. */
    @Query("select g from Pokemon p join p.games g where p.id = :pokemonId order by g.releaseOrder")
    List<Game> findByPokemonId(Integer pokemonId);
}
