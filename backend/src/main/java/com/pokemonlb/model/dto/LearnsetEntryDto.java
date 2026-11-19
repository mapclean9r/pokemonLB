package com.pokemonlb.model.dto;

import com.pokemonlb.model.entity.PokemonMove;

/**
 * One move in a Pokémon's learnset for a game.
 * level is only meaningful for "Level up" (0 = learned on evolution); it is null for other methods.
 */
public record LearnsetEntryDto(MoveDto move, String learnMethod, Short level) {

    private static final int LEVEL_UP = 1;

    public static LearnsetEntryDto from(PokemonMove pokemonMove) {
        boolean levelUp = pokemonMove.getLearnMethod().getId() == LEVEL_UP;
        return new LearnsetEntryDto(
                MoveDto.from(pokemonMove.getMove()),
                pokemonMove.getLearnMethod().getName(),
                levelUp ? pokemonMove.getLevel() : null);
    }
}
