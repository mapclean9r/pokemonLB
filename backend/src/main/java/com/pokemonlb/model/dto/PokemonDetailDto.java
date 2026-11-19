package com.pokemonlb.model.dto;

import com.pokemonlb.model.entity.Pokemon;
import java.util.List;

/** One Pokémon with its typing and base stats. types is in slot order: primary first. */
public record PokemonDetailDto(
        Integer id, String name, Short generation, List<String> types, String imageUrl, PokemonStatsDto stats) {

    public static PokemonDetailDto from(Pokemon pokemon, PokemonStatsDto stats) {
        return new PokemonDetailDto(pokemon.getId(), pokemon.getName(), pokemon.getGeneration(),
                pokemon.getTypeNames(), pokemon.getImageUrl(), stats);
    }
}
