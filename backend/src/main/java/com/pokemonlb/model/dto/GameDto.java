package com.pokemonlb.model.dto;

import com.pokemonlb.model.entity.Game;
import com.pokemonlb.model.entity.MoveGameAvailability;

public record GameDto(Integer id, String name, Short generation) {

    public static GameDto from(Game game) {
        return new GameDto(game.getId(), game.getName(), game.getGeneration());
    }

    public static GameDto from(MoveGameAvailability availability) {
        return new GameDto(availability.getGameId(), availability.getGameName(), availability.getGeneration());
    }
}
