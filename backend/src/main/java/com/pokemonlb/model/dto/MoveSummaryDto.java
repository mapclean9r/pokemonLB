package com.pokemonlb.model.dto;

import com.pokemonlb.model.entity.MoveGameAvailability;

/** A move in a list. */
public record MoveSummaryDto(Integer id, String name, String type) {

    public static MoveSummaryDto from(MoveGameAvailability availability) {
        return new MoveSummaryDto(availability.getMoveId(), availability.getMoveName(), availability.getMoveType());
    }
}
