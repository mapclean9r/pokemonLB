package com.pokemonlb.model.dto;

import com.pokemonlb.model.entity.Type;

/** A Pokémon type. generation is when it was introduced. */
public record TypeDto(Integer id, String name, Short generation) {

    public static TypeDto from(Type type) {
        return new TypeDto(type.getId(), type.getName(), type.getGeneration());
    }
}
