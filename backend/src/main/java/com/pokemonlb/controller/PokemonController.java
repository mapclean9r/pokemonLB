package com.pokemonlb.controller;

import com.pokemonlb.config.ApiUrls;
import com.pokemonlb.model.dto.EvolutionDto;
import com.pokemonlb.model.dto.GameDto;
import com.pokemonlb.model.dto.LearnsetEntryDto;
import com.pokemonlb.model.dto.MoveLevelByGenerationDto;
import com.pokemonlb.model.dto.PageDto;
import com.pokemonlb.model.dto.PokemonAbilityDto;
import com.pokemonlb.model.dto.PokemonDetailDto;
import com.pokemonlb.model.dto.PokemonSummaryDto;
import com.pokemonlb.service.PokemonService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiUrls.Pokemon.BASE)
@RequiredArgsConstructor
public class PokemonController {

    static final int DEFAULT_PAGE_SIZE = 20;
    static final int MAX_PAGE_SIZE = 100;

    private final PokemonService pokemonService;

    /**
     * GET /api/v1/pokemon?page=0&size=20&search=char&type=Fire&generation=1&gameId=25
     * One page of Pokémon in Pokédex order; every filter is optional.
     */
    @GetMapping
    public PageDto<PokemonSummaryDto> findPage(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "" + DEFAULT_PAGE_SIZE) @Min(1) @Max(MAX_PAGE_SIZE) int size,
            @RequestParam(required = false) @Size(max = 50) String search,
            @RequestParam(required = false) @Size(max = 20) String type,
            @RequestParam(required = false) Short generation,
            @RequestParam(required = false) Integer gameId) {
        return pokemonService.findPage(search, type, generation, gameId, page, size);
    }

    /** GET /api/v1/pokemon/25 */
    @GetMapping(ApiUrls.Pokemon.BY_ID)
    public PokemonDetailDto findById(@PathVariable Integer id) {
        return pokemonService.findById(id);
    }

    /** GET /api/v1/pokemon/25/games */
    @GetMapping(ApiUrls.Pokemon.GAMES)
    public List<GameDto> findGames(@PathVariable Integer id) {
        return pokemonService.findGames(id);
    }

    /** GET /api/v1/pokemon/25/moves?gameId=25 */
    @GetMapping(ApiUrls.Pokemon.MOVES)
    public List<LearnsetEntryDto> findLearnset(@PathVariable Integer id, @RequestParam Integer gameId) {
        return pokemonService.findLearnset(id, gameId);
    }

    /** GET /api/v1/pokemon/25/moves/85/levels */
    @GetMapping(ApiUrls.Pokemon.MOVE_LEVELS)
    public List<MoveLevelByGenerationDto> findMoveLevels(@PathVariable Integer id, @PathVariable Integer moveId) {
        return pokemonService.findMoveLevels(id, moveId);
    }

    /** GET /api/v1/pokemon/5/evolutions — the whole family: Charmander -> Charmeleon -> Charizard */
    @GetMapping(ApiUrls.Pokemon.EVOLUTIONS)
    public List<EvolutionDto> findEvolutions(@PathVariable Integer id) {
        return pokemonService.findEvolutions(id);
    }

    /** GET /api/v1/pokemon/25/abilities — regular abilities first, hidden ability last */
    @GetMapping(ApiUrls.Pokemon.ABILITIES)
    public List<PokemonAbilityDto> findAbilities(@PathVariable Integer id) {
        return pokemonService.findAbilities(id);
    }
}
