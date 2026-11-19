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
import com.pokemonlb.model.dto.GameDto;
import com.pokemonlb.model.dto.ItemDetailDto;
import com.pokemonlb.model.dto.ItemSummaryDto;
import com.pokemonlb.service.ItemService;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ItemController.class)
class ItemControllerTest {

    private static final ItemSummaryDto MASTER_BALL = new ItemSummaryDto(1, "Master Ball", "Standard balls");

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ItemService itemService;

    @Nested
    class FindAll {

        @Test
        void returnsAllItemsWithoutFilter() throws Exception {
            given(itemService.findAll(null)).willReturn(List.of(
                    MASTER_BALL, new ItemSummaryDto(2, "Ultra Ball", "Standard balls")));

            mockMvc.perform(get("/api/v1/items"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].id").value(1))
                    .andExpect(jsonPath("$[0].name").value("Master Ball"))
                    .andExpect(jsonPath("$[0].category").value("Standard balls"));
        }

        @Test
        void passesGenerationFilter() throws Exception {
            given(itemService.findAll((short) 9)).willReturn(List.of(MASTER_BALL));

            mockMvc.perform(get("/api/v1/items").param("generation", "9"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)));

            verify(itemService).findAll((short) 9);
        }

        @Test
        void rejectsNonNumericGeneration() throws Exception {
            mockMvc.perform(get("/api/v1/items").param("generation", "nine"))
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(itemService);
        }
    }

    @Nested
    class FindById {

        @Test
        void returnsItemWithGenerationsAndGames() throws Exception {
            given(itemService.findById(1)).willReturn(new ItemDetailDto(
                    1, "Master Ball", "Standard balls",
                    List.of((short) 1, (short) 2, (short) 3),
                    List.of(new GameDto(5, "Ruby / Sapphire", (short) 3))));

            mockMvc.perform(get("/api/v1/items/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("Master Ball"))
                    .andExpect(jsonPath("$.category").value("Standard balls"))
                    .andExpect(jsonPath("$.generations", contains(1, 2, 3)))
                    .andExpect(jsonPath("$.games", hasSize(1)))
                    .andExpect(jsonPath("$.games[0].name").value("Ruby / Sapphire"));
        }

        @Test
        void returns404WhenItemDoesNotExist() throws Exception {
            given(itemService.findById(99999)).willThrow(new NotFoundException("Item", 99999));

            mockMvc.perform(get("/api/v1/items/99999"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.detail").value("Item 99999 not found"));
        }

        @Test
        void rejectsNonNumericId() throws Exception {
            mockMvc.perform(get("/api/v1/items/abc"))
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(itemService);
        }
    }
}
