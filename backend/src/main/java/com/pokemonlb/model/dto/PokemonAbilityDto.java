package com.pokemonlb.model.dto;

import com.pokemonlb.model.entity.PokemonAbility;

/** One of a Pokémon's abilities. slot 1 and 2 are regular abilities, slot 3 the hidden one. */
public record PokemonAbilityDto(AbilityDto ability, Short slot, Boolean hidden) {

    public static PokemonAbilityDto from(PokemonAbility pokemonAbility) {
        return new PokemonAbilityDto(
                AbilityDto.from(pokemonAbility.getAbility()), pokemonAbility.getSlot(), pokemonAbility.isHidden());
    }
}
