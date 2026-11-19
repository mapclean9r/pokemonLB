package com.pokemonlb.controller;

import com.pokemonlb.config.ApiUrls;
import com.pokemonlb.model.dto.GameDto;
import com.pokemonlb.model.dto.MoveDto;
import com.pokemonlb.service.MoveService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiUrls.Moves.BASE)
@RequiredArgsConstructor
public class MoveController {

    private final MoveService moveService;

    /** GET /api/v1/moves?type=Electric (type optional) */
    @GetMapping
    public List<MoveDto> findAll(@RequestParam(required = false) String type) {
        return moveService.findAll(type);
    }

    /** GET /api/v1/moves/85 */
    @GetMapping(ApiUrls.Moves.BY_ID)
    public MoveDto findById(@PathVariable Integer id) {
        return moveService.findById(id);
    }

    /** GET /api/v1/moves/85/games */
    @GetMapping(ApiUrls.Moves.GAMES)
    public List<GameDto> findGames(@PathVariable Integer id) {
        return moveService.findGames(id);
    }
}
