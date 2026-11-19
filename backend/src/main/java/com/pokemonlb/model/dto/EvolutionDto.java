package com.pokemonlb.model.dto;

import com.pokemonlb.model.entity.PokemonEvolution;

/**
 * One evolution step: {@code from} evolves into {@code to}.
 * Only the conditions that apply are non-null; e.g. a level-up evolution has just
 * trigger "Level up" and minLevel, a stone evolution has trigger "Use item" and item.
 */
public record EvolutionDto(
        PokemonRefDto from,
        PokemonRefDto to,
        String trigger,
        Short minLevel,
        String item,
        String heldItem,
        String knownMove,
        String knownMoveType,
        Short minHappiness,
        String timeOfDay,
        String gender,
        String otherConditions) {

    public static EvolutionDto from(PokemonEvolution evolution) {
        return new EvolutionDto(
                PokemonRefDto.from(evolution.getFrom()),
                PokemonRefDto.from(evolution.getTo()),
                evolution.getTrigger(),
                evolution.getMinLevel(),
                evolution.getItem(),
                evolution.getHeldItem(),
                evolution.getKnownMove(),
                evolution.getKnownMoveType(),
                evolution.getMinHappiness(),
                evolution.getTimeOfDay(),
                evolution.getGender(),
                evolution.getOtherConditions());
    }
}
