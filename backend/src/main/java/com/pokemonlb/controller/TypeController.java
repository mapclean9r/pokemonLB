package com.pokemonlb.controller;

import com.pokemonlb.config.ApiUrls;
import com.pokemonlb.model.dto.PokemonSummaryDto;
import com.pokemonlb.model.dto.TypeDto;
import com.pokemonlb.service.TypeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiUrls.Types.BASE)
@RequiredArgsConstructor
public class TypeController {

    private final TypeService typeService;

    /** GET /api/v1/types */
    @GetMapping
    public List<TypeDto> findAll() {
        return typeService.findAll();
    }

    /** GET /api/v1/types/10 */
    @GetMapping(ApiUrls.Types.BY_ID)
    public TypeDto findById(@PathVariable Integer id) {
        return typeService.findById(id);
    }

    /** GET /api/v1/types/10/pokemon */
    @GetMapping(ApiUrls.Types.POKEMON)
    public List<PokemonSummaryDto> findPokemon(@PathVariable Integer id) {
        return typeService.findPokemon(id);
    }
}
