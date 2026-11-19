package com.pokemonlb.model.dto;

import com.pokemonlb.model.entity.Pokemon;

/** A short reference to a Pokémon, used inside other responses. */
public record PokemonRefDto(Integer id, String name, String imageUrl) {

    public static PokemonRefDto from(Pokemon pokemon) {
        return new PokemonRefDto(pokemon.getId(), pokemon.getName(), pokemon.getImageUrl());
    }
}
