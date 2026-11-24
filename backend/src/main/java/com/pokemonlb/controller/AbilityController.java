package com.pokemonlb.controller;

import com.pokemonlb.config.ApiUrls;
import com.pokemonlb.model.dto.AbilityDto;
import com.pokemonlb.model.dto.AbilityPokemonDto;
import com.pokemonlb.service.AbilityService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiUrls.Abilities.BASE)
@RequiredArgsConstructor
public class AbilityController {

    private final AbilityService abilityService;

    /** GET /api/v1/abilities?generation=3 (generation optional) */
    @GetMapping
    public List<AbilityDto> findAll(@RequestParam(required = false) Short generation) {
        return abilityService.findAll(generation);
    }

    /** GET /api/v1/abilities/9 */
    @GetMapping(ApiUrls.Abilities.BY_ID)
    public AbilityDto findById(@PathVariable Integer id) {
        return abilityService.findById(id);
    }

    /** GET /api/v1/abilities/9/pokemon */
    @GetMapping(ApiUrls.Abilities.POKEMON)
    public List<AbilityPokemonDto> findPokemon(@PathVariable Integer id) {
        return abilityService.findPokemon(id);
    }
}
