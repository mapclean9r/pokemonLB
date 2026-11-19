package com.pokemonlb.controller;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pokemonlb.exception.NotFoundException;
import com.pokemonlb.model.dto.EvolutionDto;
import com.pokemonlb.model.dto.GameDto;
import com.pokemonlb.model.dto.LearnsetEntryDto;
import com.pokemonlb.model.dto.MoveDto;
import com.pokemonlb.model.dto.MoveLevelByGenerationDto;
import com.pokemonlb.model.dto.PokemonDetailDto;
import com.pokemonlb.model.dto.PokemonRefDto;
import com.pokemonlb.model.dto.PokemonStatsDto;
import com.pokemonlb.model.dto.PokemonSummaryDto;
import com.pokemonlb.service.PokemonService;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PokemonController.class)
class PokemonControllerTest {

    private static final String IMAGE = "https://example.test/25.png";
    private static final PokemonSummaryDto PIKACHU =
            new PokemonSummaryDto(25, "Pikachu", (short) 1, List.of("Electric"), IMAGE);
    private static final MoveDto THUNDERBOLT =
            new MoveDto(85, "Thunderbolt", "Electric", "Special", (short) 90, (short) 100, (short) 15, (short) 0, (short) 1);

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PokemonService pokemonService;

    @Nested
    class FindAll {

        @Test
        void returnsAllPokemonWithoutFilters() throws Exception {
            given(pokemonService.findAll(null, null)).willReturn(List.of(PIKACHU));

            mockMvc.perform(get("/api/v1/pokemon"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].id").value(25))
                    .andExpect(jsonPath("$[0].name").value("Pikachu"))
                    .andExpect(jsonPath("$[0].generation").value(1))
                    .andExpect(jsonPath("$[0].types", contains("Electric")))
                    .andExpect(jsonPath("$[0].imageUrl").value(IMAGE));
        }

        @Test
        void passesGenerationAndGameFilters() throws Exception {
            given(pokemonService.findAll((short) 1, 25)).willReturn(List.of(PIKACHU));

            mockMvc.perform(get("/api/v1/pokemon").param("generation", "1").param("gameId", "25"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].name").value("Pikachu"));

            verify(pokemonService).findAll((short) 1, 25);
        }

        @Test
        void returnsEmptyListWhenNothingMatches() throws Exception {
            given(pokemonService.findAll((short) 9, null)).willReturn(List.of());

            mockMvc.perform(get("/api/v1/pokemon").param("generation", "9"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(0)));
        }

        @Test
        void rejectsNonNumericGeneration() throws Exception {
            mockMvc.perform(get("/api/v1/pokemon").param("generation", "abc"))
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(pokemonService);
        }
    }

    @Nested
    class FindById {

        @Test
        void returnsPokemonWithStats() throws Exception {
            PokemonStatsDto stats = new PokemonStatsDto(
                    (short) 35, (short) 55, (short) 40, (short) 50, (short) 50, (short) 90, (short) 320);
            given(pokemonService.findById(25))
                    .willReturn(new PokemonDetailDto(25, "Pikachu", (short) 1, List.of("Electric"), IMAGE, stats));

            mockMvc.perform(get("/api/v1/pokemon/25"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("Pikachu"))
                    .andExpect(jsonPath("$.imageUrl").value(IMAGE))
                    .andExpect(jsonPath("$.stats.hp").value(35))
                    .andExpect(jsonPath("$.stats.specialAttack").value(50))
                    .andExpect(jsonPath("$.stats.speed").value(90))
                    .andExpect(jsonPath("$.stats.total").value(320));
        }

        @Test
        void returnsTypesPrimaryFirst() throws Exception {
            given(pokemonService.findById(6)).willReturn(new PokemonDetailDto(
                    6, "Charizard", (short) 1, List.of("Fire", "Flying"), "https://example.test/6.png", null));

            mockMvc.perform(get("/api/v1/pokemon/6"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.types", contains("Fire", "Flying")));
        }

        @Test
        void returns404WhenPokemonDoesNotExist() throws Exception {
            given(pokemonService.findById(9999)).willThrow(new NotFoundException("Pokémon", 9999));

            mockMvc.perform(get("/api/v1/pokemon/9999"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.status").value(404))
                    .andExpect(jsonPath("$.detail").value("Pokémon 9999 not found"));
        }

        @Test
        void rejectsNonNumericId() throws Exception {
            mockMvc.perform(get("/api/v1/pokemon/abc"))
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(pokemonService);
        }
    }

    @Nested
    class FindGames {

        @Test
        void returnsGamesForPokemon() throws Exception {
            given(pokemonService.findGames(25)).willReturn(List.of(
                    new GameDto(1, "Red / Blue", (short) 1),
                    new GameDto(25, "Scarlet / Violet", (short) 9)));

            mockMvc.perform(get("/api/v1/pokemon/25/games"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].name").value("Red / Blue"))
                    .andExpect(jsonPath("$[1].id").value(25))
                    .andExpect(jsonPath("$[1].generation").value(9));
        }

        @Test
        void returns404WhenPokemonDoesNotExist() throws Exception {
            given(pokemonService.findGames(9999)).willThrow(new NotFoundException("Pokémon", 9999));

            mockMvc.perform(get("/api/v1/pokemon/9999/games"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    class FindLearnset {

        @Test
        void returnsLearnsetForGame() throws Exception {
            given(pokemonService.findLearnset(25, 25)).willReturn(List.of(
                    new LearnsetEntryDto(THUNDERBOLT, "Level up", (short) 36),
                    new LearnsetEntryDto(THUNDERBOLT, "Machine", null)));

            mockMvc.perform(get("/api/v1/pokemon/25/moves").param("gameId", "25"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].move.name").value("Thunderbolt"))
                    .andExpect(jsonPath("$[0].move.type").value("Electric"))
                    .andExpect(jsonPath("$[0].learnMethod").value("Level up"))
                    .andExpect(jsonPath("$[0].level").value(36))
                    .andExpect(jsonPath("$[1].learnMethod").value("Machine"))
                    .andExpect(jsonPath("$[1].level").value(nullValue()));
        }

        @Test
        void requiresGameId() throws Exception {
            mockMvc.perform(get("/api/v1/pokemon/25/moves"))
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(pokemonService);
        }

        @Test
        void returns404WhenGameDoesNotExist() throws Exception {
            given(pokemonService.findLearnset(25, 999)).willThrow(new NotFoundException("Game", 999));

            mockMvc.perform(get("/api/v1/pokemon/25/moves").param("gameId", "999"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.detail").value("Game 999 not found"));
        }
    }

    @Nested
    class FindMoveLevels {

        @Test
        void returnsLevelsPerGeneration() throws Exception {
            given(pokemonService.findMoveLevels(25, 85)).willReturn(List.of(
                    new MoveLevelByGenerationDto((short) 1, (short) 26, (short) 26),
                    new MoveLevelByGenerationDto((short) 7, (short) 21, (short) 42)));

            mockMvc.perform(get("/api/v1/pokemon/25/moves/85/levels"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].generation").value(1))
                    .andExpect(jsonPath("$[0].minLevel").value(26))
                    .andExpect(jsonPath("$[1].minLevel").value(21))
                    .andExpect(jsonPath("$[1].maxLevel").value(42));
        }

        @Test
        void returns404WhenMoveDoesNotExist() throws Exception {
            given(pokemonService.findMoveLevels(any(), any())).willThrow(new NotFoundException("Move", 9999));

            mockMvc.perform(get("/api/v1/pokemon/25/moves/9999/levels"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.detail").value("Move 9999 not found"));
        }
    }

    @Nested
    class FindEvolutions {

        private final PokemonRefDto charmander = new PokemonRefDto(4, "Charmander", "https://example.test/4.png");
        private final PokemonRefDto charmeleon = new PokemonRefDto(5, "Charmeleon", "https://example.test/5.png");
        private final PokemonRefDto charizard = new PokemonRefDto(6, "Charizard", "https://example.test/6.png");

        @Test
        void returnsWholeFamilyWithLevels() throws Exception {
            given(pokemonService.findEvolutions(5)).willReturn(List.of(
                    levelUp(charmander, charmeleon, 16),
                    levelUp(charmeleon, charizard, 36)));

            mockMvc.perform(get("/api/v1/pokemon/5/evolutions"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].from.name").value("Charmander"))
                    .andExpect(jsonPath("$[0].to.name").value("Charmeleon"))
                    .andExpect(jsonPath("$[0].trigger").value("Level up"))
                    .andExpect(jsonPath("$[0].minLevel").value(16))
                    .andExpect(jsonPath("$[1].to.id").value(6))
                    .andExpect(jsonPath("$[1].minLevel").value(36))
                    .andExpect(jsonPath("$[1].item").value(nullValue()));
        }

        @Test
        void returnsItemForStoneEvolution() throws Exception {
            PokemonRefDto pikachu = new PokemonRefDto(25, "Pikachu", "https://example.test/25.png");
            PokemonRefDto raichu = new PokemonRefDto(26, "Raichu", "https://example.test/26.png");
            given(pokemonService.findEvolutions(25)).willReturn(List.of(new EvolutionDto(
                    pikachu, raichu, "Use item", null, "Thunder Stone", null, null, null, null, null, null, null)));

            mockMvc.perform(get("/api/v1/pokemon/25/evolutions"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].trigger").value("Use item"))
                    .andExpect(jsonPath("$[0].item").value("Thunder Stone"))
                    .andExpect(jsonPath("$[0].minLevel").value(nullValue()));
        }

        @Test
        void returnsEmptyListForPokemonThatDoesNotEvolve() throws Exception {
            given(pokemonService.findEvolutions(128)).willReturn(List.of());

            mockMvc.perform(get("/api/v1/pokemon/128/evolutions"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(0)));
        }

        @Test
        void returns404WhenPokemonDoesNotExist() throws Exception {
            given(pokemonService.findEvolutions(9999)).willThrow(new NotFoundException("Pokémon", 9999));

            mockMvc.perform(get("/api/v1/pokemon/9999/evolutions"))
                    .andExpect(status().isNotFound());
        }

        private EvolutionDto levelUp(PokemonRefDto from, PokemonRefDto to, int level) {
            return new EvolutionDto(from, to, "Level up", (short) level, null, null, null, null, null, null, null, null);
        }
    }
}
