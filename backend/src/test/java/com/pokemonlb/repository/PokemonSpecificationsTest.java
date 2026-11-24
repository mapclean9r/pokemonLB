package com.pokemonlb.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/** The Java-side normalisation must match unaccent(lower(name)) in PostgreSQL. */
class PokemonSpecificationsTest {

    @Test
    void normalizeRemovesAccentsAndCase() {
        assertThat(PokemonSpecifications.normalize("Flabébé")).isEqualTo("flabebe");
        assertThat(PokemonSpecifications.normalize("PIKACHU")).isEqualTo("pikachu");
    }

    @Test
    void escapeLikeMakesWildcardsLiteral() {
        assertThat(PokemonSpecifications.escapeLike("50%_off\\")).isEqualTo("50\\%\\_off\\\\");
    }
}
