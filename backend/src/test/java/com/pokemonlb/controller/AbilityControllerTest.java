package com.pokemonlb.controller;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pokemonlb.exception.NotFoundException;
import com.pokemonlb.model.dto.AbilityDto;
import com.pokemonlb.model.dto.AbilityPokemonDto;
import com.pokemonlb.model.dto.PokemonSummaryDto;
import com.pokemonlb.service.AbilityService;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AbilityController.class)
class AbilityControllerTest {

    private static final AbilityDto STATIC =
            new AbilityDto(9, "Static", (short) 3, "Has a 30% chance of paralyzing attacking Pokémon on contact.");
    private static final AbilityDto LIGHTNING_ROD = new AbilityDto(31, "Lightning Rod", (short) 3,
            "Redirects single-target Electric moves to this Pokémon where possible. Absorbs Electric moves, raising Special Attack one stage.");

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AbilityService abilityService;

    @Nested
    class FindAll {

        @Test
        void returnsAllAbilitiesWithoutFilter() throws Exception {
            given(abilityService.findAll(null)).willReturn(List.of(LIGHTNING_ROD, STATIC));

            mockMvc.perform(get("/api/v1/abilities"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].name").value("Lightning Rod"))
                    .andExpect(jsonPath("$[1].id").value(9))
                    .andExpect(jsonPath("$[1].generation").value(3))
                    .andExpect(jsonPath("$[1].effect").value(STATIC.effect()));
        }

        @Test
        void passesGenerationFilter() throws Exception {
            given(abilityService.findAll((short) 3)).willReturn(List.of(STATIC));

            mockMvc.perform(get("/api/v1/abilities").param("generation", "3"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)));

            verify(abilityService).findAll((short) 3);
        }

        @Test
        void rejectsNonNumericGeneration() throws Exception {
            mockMvc.perform(get("/api/v1/abilities").param("generation", "three"))
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(abilityService);
        }
    }

    @Nested
    class FindById {

        @Test
        void returnsAbility() throws Exception {
            given(abilityService.findById(9)).willReturn(STATIC);

            mockMvc.perform(get("/api/v1/abilities/9"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(9))
                    .andExpect(jsonPath("$.name").value("Static"))
                    .andExpect(jsonPath("$.generation").value(3));
        }

        @Test
        void returns404WhenAbilityDoesNotExist() throws Exception {
            given(abilityService.findById(9999)).willThrow(new NotFoundException("Ability", 9999));

            mockMvc.perform(get("/api/v1/abilities/9999"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.detail").value("Ability 9999 not found"));
        }

        @Test
        void rejectsNonNumericId() throws Exception {
            mockMvc.perform(get("/api/v1/abilities/static"))
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(abilityService);
        }
    }

    @Nested
    class FindPokemon {

        @Test
        void returnsPokemonMarkedHiddenOrNot() throws Exception {
            given(abilityService.findPokemon(31)).willReturn(List.of(
                    new AbilityPokemonDto(new PokemonSummaryDto(
                            25, "Pikachu", (short) 1, List.of("Electric"), "https://example.test/25.png"), true),
                    new AbilityPokemonDto(new PokemonSummaryDto(
                            111, "Rhyhorn", (short) 1, List.of("Ground", "Rock"), "https://example.test/111.png"), false)));

            mockMvc.perform(get("/api/v1/abilities/31/pokemon"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].pokemon.name").value("Pikachu"))
                    .andExpect(jsonPath("$[0].hidden").value(true))
                    .andExpect(jsonPath("$[1].pokemon.types", contains("Ground", "Rock")))
                    .andExpect(jsonPath("$[1].hidden").value(false));
        }

        @Test
        void returns404WhenAbilityDoesNotExist() throws Exception {
            given(abilityService.findPokemon(9999)).willThrow(new NotFoundException("Ability", 9999));

            mockMvc.perform(get("/api/v1/abilities/9999/pokemon"))
                    .andExpect(status().isNotFound());
        }
    }
}
