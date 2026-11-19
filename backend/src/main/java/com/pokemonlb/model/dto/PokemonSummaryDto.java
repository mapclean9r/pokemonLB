package com.pokemonlb.model.dto;

import com.pokemonlb.model.entity.Pokemon;
import java.util.List;

/** A Pokémon in a list. types is in slot order: primary first, e.g. ["Fire", "Flying"]. */
public record PokemonSummaryDto(Integer id, String name, Short generation, List<String> types, String imageUrl) {

    public static PokemonSummaryDto from(Pokemon pokemon) {
        return new PokemonSummaryDto(pokemon.getId(), pokemon.getName(), pokemon.getGeneration(),
                pokemon.getTypeNames(), pokemon.getImageUrl());
    }
}
