package com.pokemonlb.controller;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pokemonlb.exception.NotFoundException;
import com.pokemonlb.model.dto.PokemonSummaryDto;
import com.pokemonlb.model.dto.TypeDto;
import com.pokemonlb.service.TypeService;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TypeController.class)
class TypeControllerTest {

    private static final TypeDto FIRE = new TypeDto(10, "Fire", (short) 1);

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TypeService typeService;

    @Nested
    class FindAll {

        @Test
        void returnsAllTypes() throws Exception {
            given(typeService.findAll()).willReturn(List.of(
                    new TypeDto(1, "Normal", (short) 1), FIRE, new TypeDto(18, "Fairy", (short) 6)));

            mockMvc.perform(get("/api/v1/types"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(3)))
                    .andExpect(jsonPath("$[1].id").value(10))
                    .andExpect(jsonPath("$[1].name").value("Fire"))
                    .andExpect(jsonPath("$[2].generation").value(6));
        }
    }

    @Nested
    class FindById {

        @Test
        void returnsType() throws Exception {
            given(typeService.findById(10)).willReturn(FIRE);

            mockMvc.perform(get("/api/v1/types/10"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(10))
                    .andExpect(jsonPath("$.name").value("Fire"))
                    .andExpect(jsonPath("$.generation").value(1));
        }

        @Test
        void returns404WhenTypeDoesNotExist() throws Exception {
            given(typeService.findById(99)).willThrow(new NotFoundException("Type", 99));

            mockMvc.perform(get("/api/v1/types/99"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.detail").value("Type 99 not found"));
        }

        @Test
        void rejectsNonNumericId() throws Exception {
            mockMvc.perform(get("/api/v1/types/fire"))
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(typeService);
        }
    }

    @Nested
    class FindPokemon {

        @Test
        void returnsPokemonWithType() throws Exception {
            given(typeService.findPokemon(10)).willReturn(List.of(
                    new PokemonSummaryDto(4, "Charmander", (short) 1, List.of("Fire"), "https://example.test/4.png"),
                    new PokemonSummaryDto(6, "Charizard", (short) 1, List.of("Fire", "Flying"), "https://example.test/6.png")));

            mockMvc.perform(get("/api/v1/types/10/pokemon"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].name").value("Charmander"))
                    .andExpect(jsonPath("$[1].types", contains("Fire", "Flying")));
        }

        @Test
        void returns404WhenTypeDoesNotExist() throws Exception {
            given(typeService.findPokemon(99)).willThrow(new NotFoundException("Type", 99));

            mockMvc.perform(get("/api/v1/types/99/pokemon"))
                    .andExpect(status().isNotFound());
        }
    }
}
