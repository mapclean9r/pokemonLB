package com.pokemonlb.model.dto;

import com.pokemonlb.model.entity.Ability;

/** An ability. generation is when it was introduced; effect is a short description. */
public record AbilityDto(Integer id, String name, Short generation, String effect) {

    public static AbilityDto from(Ability ability) {
        return new AbilityDto(ability.getId(), ability.getName(), ability.getGeneration(), ability.getEffect());
    }
}
