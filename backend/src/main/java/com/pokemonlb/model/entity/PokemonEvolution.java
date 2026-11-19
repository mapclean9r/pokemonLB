package com.pokemonlb.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** One evolution step: {@code from} evolves into {@code to}. Only the conditions that apply are non-null. */
@Entity
@Table(name = "pokemon_evolution")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PokemonEvolution {

    @Id
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "from_pokemon_id", nullable = false)
    private Pokemon from;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "to_pokemon_id", nullable = false)
    private Pokemon to;

    /** What triggers it: "Level up", "Use item", "Trade or Linking Cord", ... */
    @Column(nullable = false, length = 100)
    private String trigger;

    @Column(name = "min_level", columnDefinition = "SMALLINT")
    private Short minLevel;

    @Column(length = 50)
    private String item;

    @Column(name = "held_item", length = 50)
    private String heldItem;

    @Column(name = "known_move", length = 50)
    private String knownMove;

    @Column(name = "known_move_type", length = 20)
    private String knownMoveType;

    @Column(name = "min_happiness", columnDefinition = "SMALLINT")
    private Short minHappiness;

    @Column(name = "time_of_day", length = 10)
    private String timeOfDay;

    @Column(length = 10)
    private String gender;

    /** Rarer conditions in words, e.g. "While it is raining" or "50% chance". */
    @Column(name = "other_conditions", columnDefinition = "TEXT")
    private String otherConditions;
}
