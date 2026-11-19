package com.pokemonlb.model.dto;

import java.util.List;

/**
 * One item and where it exists. generations is complete; games only lists games PokeAPI has
 * per-game item data for (mostly up to Sword / Shield, plus TMs in later games).
 */
public record ItemDetailDto(Integer id, String name, String category, List<Short> generations, List<GameDto> games) {}
