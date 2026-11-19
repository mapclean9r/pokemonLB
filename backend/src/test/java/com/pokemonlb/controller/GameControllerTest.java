package com.pokemonlb.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pokemonlb.exception.NotFoundException;
import com.pokemonlb.model.dto.GameDto;
import com.pokemonlb.model.dto.ItemSummaryDto;
import com.pokemonlb.model.dto.MoveSummaryDto;
import com.pokemonlb.model.dto.PokemonSummaryDto;
import com.pokemonlb.service.GameService;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(GameController.class)
class GameControllerTest {

    private static final GameDto SCARLET_VIOLET = new GameDto(25, "Scarlet / Violet", (short) 9);

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GameService gameService;

    @Nested
    class FindAll {

        @Test
        void returnsAllGames() throws Exception {
            given(gameService.findAll()).willReturn(List.of(
                    new GameDto(1, "Red / Blue", (short) 1), SCARLET_VIOLET));

            mockMvc.perform(get("/api/v1/games"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].name").value("Red / Blue"))
                    .andExpect(jsonPath("$[1].id").value(25))
                    .andExpect(jsonPath("$[1].generation").value(9));
        }
    }

    @Nested
    class FindById {

        @Test
        void returnsGame() throws Exception {
            given(gameService.findById(25)).willReturn(SCARLET_VIOLET);

            mockMvc.perform(get("/api/v1/games/25"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(25))
                    .andExpect(jsonPath("$.name").value("Scarlet / Violet"))
                    .andExpect(jsonPath("$.generation").value(9));
        }

        @Test
        void returns404WhenGameDoesNotExist() throws Exception {
            given(gameService.findById(999)).willThrow(new NotFoundException("Game", 999));

            mockMvc.perform(get("/api/v1/games/999"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.detail").value("Game 999 not found"));
        }

        @Test
        void rejectsNonNumericId() throws Exception {
            mockMvc.perform(get("/api/v1/games/abc"))
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(gameService);
        }
    }

    @Nested
    class FindPokemon {

        @Test
        void returnsPokemonInGame() throws Exception {
            given(gameService.findPokemon(25)).willReturn(List.of(
                    new PokemonSummaryDto(25, "Pikachu", (short) 1, List.of("Electric"), "https://example.test/25.png")));

            mockMvc.perform(get("/api/v1/games/25/pokemon"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].name").value("Pikachu"));
        }

        @Test
        void returns404WhenGameDoesNotExist() throws Exception {
            given(gameService.findPokemon(999)).willThrow(new NotFoundException("Game", 999));

            mockMvc.perform(get("/api/v1/games/999/pokemon"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    class FindMoves {

        @Test
        void returnsMovesInGame() throws Exception {
            given(gameService.findMoves(25)).willReturn(List.of(
                    new MoveSummaryDto(71, "Absorb", "Grass"),
                    new MoveSummaryDto(85, "Thunderbolt", "Electric")));

            mockMvc.perform(get("/api/v1/games/25/moves"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[1].id").value(85))
                    .andExpect(jsonPath("$[1].name").value("Thunderbolt"))
                    .andExpect(jsonPath("$[1].type").value("Electric"));
        }

        @Test
        void returns404WhenGameDoesNotExist() throws Exception {
            given(gameService.findMoves(999)).willThrow(new NotFoundException("Game", 999));

            mockMvc.perform(get("/api/v1/games/999/moves"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    class FindItems {

        @Test
        void returnsItemsInGame() throws Exception {
            given(gameService.findItems(20)).willReturn(List.of(
                    new ItemSummaryDto(1, "Master Ball", "Standard balls")));

            mockMvc.perform(get("/api/v1/games/20/items"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].id").value(1))
                    .andExpect(jsonPath("$[0].name").value("Master Ball"))
                    .andExpect(jsonPath("$[0].category").value("Standard balls"));
        }

        @Test
        void returns404WhenGameDoesNotExist() throws Exception {
            given(gameService.findItems(999)).willThrow(new NotFoundException("Game", 999));

            mockMvc.perform(get("/api/v1/games/999/items"))
                    .andExpect(status().isNotFound());
        }
    }
}
