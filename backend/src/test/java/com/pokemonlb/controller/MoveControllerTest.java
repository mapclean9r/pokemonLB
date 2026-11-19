package com.pokemonlb.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pokemonlb.exception.NotFoundException;
import com.pokemonlb.model.dto.GameDto;
import com.pokemonlb.model.dto.MoveDto;
import com.pokemonlb.service.MoveService;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MoveController.class)
class MoveControllerTest {

    private static final MoveDto THUNDERBOLT =
            new MoveDto(85, "Thunderbolt", "Electric", "Special", (short) 90, (short) 100, (short) 15, (short) 0, (short) 1);
    private static final MoveDto THUNDER_WAVE =
            new MoveDto(86, "Thunder Wave", "Electric", "Status", null, (short) 90, (short) 20, (short) 0, (short) 1);

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MoveService moveService;

    @Nested
    class FindAll {

        @Test
        void returnsAllMovesWithoutFilter() throws Exception {
            given(moveService.findAll(null)).willReturn(List.of(THUNDERBOLT, THUNDER_WAVE));

            mockMvc.perform(get("/api/v1/moves"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].name").value("Thunderbolt"))
                    .andExpect(jsonPath("$[0].damageClass").value("Special"))
                    .andExpect(jsonPath("$[0].power").value(90))
                    .andExpect(jsonPath("$[0].accuracy").value(100))
                    .andExpect(jsonPath("$[0].pp").value(15))
                    .andExpect(jsonPath("$[1].power").value(nullValue()));
        }

        @Test
        void passesTypeFilter() throws Exception {
            given(moveService.findAll("Electric")).willReturn(List.of(THUNDERBOLT));

            mockMvc.perform(get("/api/v1/moves").param("type", "Electric"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].type").value("Electric"));

            verify(moveService).findAll("Electric");
        }

        @Test
        void returnsEmptyListForUnknownType() throws Exception {
            String injectionAttempt = "Electric' OR '1'='1";
            given(moveService.findAll(injectionAttempt)).willReturn(List.of());

            mockMvc.perform(get("/api/v1/moves").param("type", injectionAttempt))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(0)));

            verify(moveService).findAll(injectionAttempt);
        }
    }

    @Nested
    class FindById {

        @Test
        void returnsMove() throws Exception {
            given(moveService.findById(85)).willReturn(THUNDERBOLT);

            mockMvc.perform(get("/api/v1/moves/85"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(85))
                    .andExpect(jsonPath("$.name").value("Thunderbolt"))
                    .andExpect(jsonPath("$.type").value("Electric"))
                    .andExpect(jsonPath("$.priority").value(0))
                    .andExpect(jsonPath("$.generation").value(1));
        }

        @Test
        void returns404WhenMoveDoesNotExist() throws Exception {
            given(moveService.findById(9999)).willThrow(new NotFoundException("Move", 9999));

            mockMvc.perform(get("/api/v1/moves/9999"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.detail").value("Move 9999 not found"));
        }

        @Test
        void rejectsNonNumericId() throws Exception {
            mockMvc.perform(get("/api/v1/moves/abc"))
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(moveService);
        }
    }

    @Nested
    class FindGames {

        @Test
        void returnsGamesForMove() throws Exception {
            given(moveService.findGames(85)).willReturn(List.of(
                    new GameDto(1, "Red / Blue", (short) 1),
                    new GameDto(25, "Scarlet / Violet", (short) 9)));

            mockMvc.perform(get("/api/v1/moves/85/games"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].name").value("Red / Blue"))
                    .andExpect(jsonPath("$[1].generation").value(9));
        }

        @Test
        void returns404WhenMoveDoesNotExist() throws Exception {
            given(moveService.findGames(9999)).willThrow(new NotFoundException("Move", 9999));

            mockMvc.perform(get("/api/v1/moves/9999/games"))
                    .andExpect(status().isNotFound());
        }
    }
}
