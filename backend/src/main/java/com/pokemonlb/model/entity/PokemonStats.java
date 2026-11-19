package com.pokemonlb.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Base stats of a Pokémon species, keyed by its National Pokédex number. */
@Entity
@Table(name = "pokemon_stats")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PokemonStats {

    @Id
    @Column(name = "pokemon_id")
    private Integer pokemonId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pokemon_id")
    private Pokemon pokemon;

    @Column(nullable = false, columnDefinition = "SMALLINT")
    private Short hp;

    @Column(nullable = false, columnDefinition = "SMALLINT")
    private Short attack;

    @Column(nullable = false, columnDefinition = "SMALLINT")
    private Short defense;

    @Column(name = "special_attack", nullable = false, columnDefinition = "SMALLINT")
    private Short specialAttack;

    @Column(name = "special_defense", nullable = false, columnDefinition = "SMALLINT")
    private Short specialDefense;

    @Column(nullable = false, columnDefinition = "SMALLINT")
    private Short speed;

    /** Computed by the database from the six stats. */
    @Column(insertable = false, updatable = false, columnDefinition = "SMALLINT")
    private Short total;
}
