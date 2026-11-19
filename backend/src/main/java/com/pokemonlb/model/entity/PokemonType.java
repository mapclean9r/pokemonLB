package com.pokemonlb.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Middle table row: a Pokémon has a type in a slot (1 = primary, 2 = secondary).
 * An entity rather than a plain @ManyToMany because the link carries the slot.
 */
@Entity
@Table(name = "pokemon_type")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PokemonType {

    @EmbeddedId
    private Id id;

    @MapsId("pokemonId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pokemon_id")
    private Pokemon pokemon;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    public short getSlot() {
        return id.getSlot();
    }

    @Embeddable
    @Getter
    @EqualsAndHashCode
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Id implements Serializable {

        @Column(name = "pokemon_id")
        private Integer pokemonId;

        @Column(name = "slot", columnDefinition = "SMALLINT")
        private short slot;
    }
}
