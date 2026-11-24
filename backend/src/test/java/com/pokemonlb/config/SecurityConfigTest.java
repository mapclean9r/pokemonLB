package com.pokemonlb.config;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pokemonlb.controller.TypeController;
import com.pokemonlb.model.dto.TypeDto;
import com.pokemonlb.service.TypeService;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/** The security rules, checked through one controller. Requests here send the key only when a test adds it. */
@WebMvcTest(TypeController.class)
@Import(SecurityConfig.class)
@TestPropertySource(properties = "pokemonlb.api-key=" + SecurityConfigTest.KEY)
class SecurityConfigTest {

    static final String KEY = "test-key-123";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TypeService typeService;

    @Nested
    class WithTheKey {

        @Test
        void getWorks() throws Exception {
            given(typeService.findAll()).willReturn(List.of(new TypeDto(10, "Fire", (short) 1)));

            mockMvc.perform(get("/api/v1/types").header(ApiKeyFilter.HEADER, KEY))
                    .andExpect(status().isOk())
                    .andExpect(header().string("X-Content-Type-Options", "nosniff"))
                    .andExpect(header().string("X-Frame-Options", "DENY"))
                    .andExpect(header().doesNotExist("Set-Cookie"));
        }

        @Test
        void unknownApiPathGives404() throws Exception {
            mockMvc.perform(get("/api/v1/nothing-here").header(ApiKeyFilter.HEADER, KEY))
                    .andExpect(status().isNotFound());
        }

        @Test
        void writesAreRefused() throws Exception {
            mockMvc.perform(post("/api/v1/types").header(ApiKeyFilter.HEADER, KEY)
                            .contentType(MediaType.APPLICATION_JSON).content("{}"))
                    .andExpect(status().isForbidden());
            mockMvc.perform(put("/api/v1/types/10").header(ApiKeyFilter.HEADER, KEY)
                            .contentType(MediaType.APPLICATION_JSON).content("{}"))
                    .andExpect(status().isForbidden());
            mockMvc.perform(delete("/api/v1/types/10").header(ApiKeyFilter.HEADER, KEY))
                    .andExpect(status().isForbidden());
        }

        @Test
        void pathsOutsideTheApiAreRefused() throws Exception {
            mockMvc.perform(get("/actuator").header(ApiKeyFilter.HEADER, KEY)).andExpect(status().isForbidden());
        }
    }

    @Nested
    class WithoutAValidKey {

        @Test
        void missingKeyGives401() throws Exception {
            mockMvc.perform(get("/api/v1/types")).andExpect(status().isUnauthorized());
        }

        @Test
        void wrongKeyGives401() throws Exception {
            mockMvc.perform(get("/api/v1/types").header(ApiKeyFilter.HEADER, "wrong-key"))
                    .andExpect(status().isUnauthorized());
            mockMvc.perform(get("/api/v1/types").header(ApiKeyFilter.HEADER, KEY + "x"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        void keyIsCaseSensitive() throws Exception {
            mockMvc.perform(get("/api/v1/types").header(ApiKeyFilter.HEADER, KEY.toUpperCase()))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        void writesGive401() throws Exception {
            mockMvc.perform(delete("/api/v1/types/10")).andExpect(status().isUnauthorized());
        }
    }
}
