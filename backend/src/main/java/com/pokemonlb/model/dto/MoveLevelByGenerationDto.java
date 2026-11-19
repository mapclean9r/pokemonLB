package com.pokemonlb.model.dto;

/**
 * The level(s) a Pokémon learns a move at by leveling up in one generation.
 * minLevel and maxLevel differ when games in that generation disagree; 0 means learned on evolution.
 */
public record MoveLevelByGenerationDto(Short generation, Short minLevel, Short maxLevel) {}
