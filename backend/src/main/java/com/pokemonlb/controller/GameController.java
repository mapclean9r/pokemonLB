package com.pokemonlb.controller;

import com.pokemonlb.config.ApiUrls;
import com.pokemonlb.model.dto.GameDto;
import com.pokemonlb.model.dto.ItemSummaryDto;
import com.pokemonlb.model.dto.MoveSummaryDto;
import com.pokemonlb.model.dto.PokemonSummaryDto;
import com.pokemonlb.service.GameService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiUrls.Games.BASE)
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    /** GET /api/v1/games */
    @GetMapping
    public List<GameDto> findAll() {
        return gameService.findAll();
    }

    /** GET /api/v1/games/25 */
    @GetMapping(ApiUrls.Games.BY_ID)
    public GameDto findById(@PathVariable Integer id) {
        return gameService.findById(id);
    }

    /** GET /api/v1/games/25/pokemon */
    @GetMapping(ApiUrls.Games.POKEMON)
    public List<PokemonSummaryDto> findPokemon(@PathVariable Integer id) {
        return gameService.findPokemon(id);
    }

    /** GET /api/v1/games/25/moves */
    @GetMapping(ApiUrls.Games.MOVES)
    public List<MoveSummaryDto> findMoves(@PathVariable Integer id) {
        return gameService.findMoves(id);
    }

    /** GET /api/v1/games/20/items */
    @GetMapping(ApiUrls.Games.ITEMS)
    public List<ItemSummaryDto> findItems(@PathVariable Integer id) {
        return gameService.findItems(id);
    }
}
