package com.pokemonlb.repository;

import com.pokemonlb.model.entity.PokemonAbility;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PokemonAbilityRepository extends JpaRepository<PokemonAbility, PokemonAbility.Id> {

    /** A Pokémon's abilities in slot order (regular first, hidden last), with the ability loaded. */
    @Query("""
            select pa from PokemonAbility pa join fetch pa.ability
            where pa.pokemon.id = :pokemonId
            order by pa.id.slot""")
    List<PokemonAbility> findByPokemonId(Integer pokemonId);

    /** Every Pokémon that can have an ability, by Pokédex number, with the Pokémon loaded. */
    @Query("""
            select pa from PokemonAbility pa join fetch pa.pokemon
            where pa.ability.id = :abilityId
            order by pa.pokemon.id""")
    List<PokemonAbility> findByAbilityId(Integer abilityId);
}
