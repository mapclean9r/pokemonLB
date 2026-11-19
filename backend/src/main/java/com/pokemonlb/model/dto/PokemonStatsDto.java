package com.pokemonlb.model.dto;

import com.pokemonlb.model.entity.PokemonStats;

public record PokemonStatsDto(
        Short hp,
        Short attack,
        Short defense,
        Short specialAttack,
        Short specialDefense,
        Short speed,
        Short total) {

    public static PokemonStatsDto from(PokemonStats stats) {
        return new PokemonStatsDto(stats.getHp(), stats.getAttack(), stats.getDefense(),
                stats.getSpecialAttack(), stats.getSpecialDefense(), stats.getSpeed(), stats.getTotal());
    }
}
