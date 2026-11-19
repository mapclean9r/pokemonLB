package com.pokemonlb.model.dto;

import com.pokemonlb.model.entity.Move;

/** power and accuracy are null for status moves and moves that never miss. */
public record MoveDto(
        Integer id,
        String name,
        String type,
        String damageClass,
        Short power,
        Short accuracy,
        Short pp,
        Short priority,
        Short generation) {

    public static MoveDto from(Move move) {
        return new MoveDto(move.getId(), move.getName(), move.getType(), move.getDamageClass(),
                move.getPower(), move.getAccuracy(), move.getPp(), move.getPriority(), move.getGeneration());
    }
}
