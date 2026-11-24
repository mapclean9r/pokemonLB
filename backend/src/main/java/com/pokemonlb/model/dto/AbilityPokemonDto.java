package com.pokemonlb.model.dto;

import com.pokemonlb.model.entity.PokemonAbility;

/** A Pokémon that can have a given ability, and whether it's that Pokémon's hidden ability. */
public record AbilityPokemonDto(PokemonSummaryDto pokemon, Boolean hidden) {

    public static AbilityPokemonDto from(PokemonAbility pokemonAbility) {
        return new AbilityPokemonDto(PokemonSummaryDto.from(pokemonAbility.getPokemon()), pokemonAbility.isHidden());
    }
}
