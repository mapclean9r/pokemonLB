package com.pokemonlb.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.pokemonlb.exception.NotFoundException;
import com.pokemonlb.model.dto.EvolutionDto;
import com.pokemonlb.model.entity.Pokemon;
import com.pokemonlb.model.entity.PokemonEvolution;
import com.pokemonlb.repository.GameRepository;
import com.pokemonlb.repository.MoveRepository;
import com.pokemonlb.repository.PokemonEvolutionRepository;
import com.pokemonlb.repository.PokemonMoveRepository;
import com.pokemonlb.repository.PokemonRepository;
import com.pokemonlb.repository.PokemonStatsRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Unit tests for how PokemonService collects a whole evolution family. */
class PokemonServiceEvolutionTest {

    private final PokemonRepository pokemonRepository = mock(PokemonRepository.class);
    private final PokemonEvolutionRepository evolutionRepository = mock(PokemonEvolutionRepository.class);
    private PokemonService service;

    private final Pokemon charmander = pokemon(4, "Charmander");
    private final Pokemon charmeleon = pokemon(5, "Charmeleon");
    private final Pokemon charizard = pokemon(6, "Charizard");
    private final PokemonEvolution charmanderToCharmeleon = levelUp(1, charmander, charmeleon, 16);
    private final PokemonEvolution charmeleonToCharizard = levelUp(2, charmeleon, charizard, 36);

    @BeforeEach
    void setUp() {
        service = new PokemonService(pokemonRepository, mock(PokemonStatsRepository.class), mock(GameRepository.class),
                mock(MoveRepository.class), mock(PokemonMoveRepository.class), evolutionRepository);

        for (Pokemon p : List.of(charmander, charmeleon, charizard)) {
            given(pokemonRepository.findById(p.getId())).willReturn(Optional.of(p));
        }
        given(evolutionRepository.findByTo_Id(4)).willReturn(Optional.empty());
        given(evolutionRepository.findByTo_Id(5)).willReturn(Optional.of(charmanderToCharmeleon));
        given(evolutionRepository.findByTo_Id(6)).willReturn(Optional.of(charmeleonToCharizard));
        given(evolutionRepository.findByFrom_IdInOrderById(List.of(4))).willReturn(List.of(charmanderToCharmeleon));
        given(evolutionRepository.findByFrom_IdInOrderById(List.of(5))).willReturn(List.of(charmeleonToCharizard));
        given(evolutionRepository.findByFrom_IdInOrderById(List.of(6))).willReturn(List.of());
    }

    @Test
    void returnsSameFamilyFromAnyStage() {
        for (int id : List.of(4, 5, 6)) {
            List<EvolutionDto> family = service.findEvolutions(id);

            assertThat(family).extracting(e -> e.from().name() + " -> " + e.to().name() + " @" + e.minLevel())
                    .as("family seen from Pokémon %d", id)
                    .containsExactly("Charmander -> Charmeleon @16", "Charmeleon -> Charizard @36");
        }
    }

    @Test
    void returnsAllBranchesOfABranchingFamily() {
        Pokemon eevee = pokemon(133, "Eevee");
        Pokemon vaporeon = pokemon(134, "Vaporeon");
        Pokemon jolteon = pokemon(135, "Jolteon");
        given(pokemonRepository.findById(133)).willReturn(Optional.of(eevee));
        given(evolutionRepository.findByTo_Id(133)).willReturn(Optional.empty());
        List<PokemonEvolution> eeveeSteps = List.of(
                stone(3, eevee, vaporeon, "Water Stone"), stone(4, eevee, jolteon, "Thunder Stone"));
        given(evolutionRepository.findByFrom_IdInOrderById(List.of(133))).willReturn(eeveeSteps);
        given(evolutionRepository.findByFrom_IdInOrderById(List.of(134, 135))).willReturn(List.of());

        assertThat(service.findEvolutions(133))
                .extracting(e -> e.to().name() + " via " + e.item())
                .containsExactly("Vaporeon via Water Stone", "Jolteon via Thunder Stone");
    }

    @Test
    void returnsEmptyListWhenPokemonDoesNotEvolve() {
        Pokemon tauros = pokemon(128, "Tauros");
        given(pokemonRepository.findById(128)).willReturn(Optional.of(tauros));
        given(evolutionRepository.findByTo_Id(128)).willReturn(Optional.empty());
        given(evolutionRepository.findByFrom_IdInOrderById(List.of(128))).willReturn(List.of());

        assertThat(service.findEvolutions(128)).isEmpty();
    }

    @Test
    void throwsNotFoundForUnknownPokemon() {
        given(pokemonRepository.findById(9999)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.findEvolutions(9999)).isInstanceOf(NotFoundException.class);
    }

    private static Pokemon pokemon(int id, String name) {
        Pokemon pokemon = mock(Pokemon.class);
        given(pokemon.getId()).willReturn(id);
        given(pokemon.getName()).willReturn(name);
        return pokemon;
    }

    private static PokemonEvolution levelUp(int id, Pokemon from, Pokemon to, int level) {
        PokemonEvolution evolution = step(id, from, to, "Level up");
        given(evolution.getMinLevel()).willReturn((short) level);
        return evolution;
    }

    private static PokemonEvolution stone(int id, Pokemon from, Pokemon to, String item) {
        PokemonEvolution evolution = step(id, from, to, "Use item");
        given(evolution.getItem()).willReturn(item);
        return evolution;
    }

    private static PokemonEvolution step(int id, Pokemon from, Pokemon to, String trigger) {
        PokemonEvolution evolution = mock(PokemonEvolution.class);
        given(evolution.getId()).willReturn(id);
        given(evolution.getFrom()).willReturn(from);
        given(evolution.getTo()).willReturn(to);
        given(evolution.getTrigger()).willReturn(trigger);
        return evolution;
    }
}
