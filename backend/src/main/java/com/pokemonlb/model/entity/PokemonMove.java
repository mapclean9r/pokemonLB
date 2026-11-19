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
 * Middle table row: a Pokémon learns a move in a game by some method.
 * It is an entity (not a plain @ManyToMany) because the link carries its own data.
 */
@Entity
@Table(name = "pokemon_move")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PokemonMove {

    @EmbeddedId
    private Id id;

    @MapsId("pokemonId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pokemon_id")
    private Pokemon pokemon;

    @MapsId("gameId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @MapsId("moveId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "move_id")
    private Move move;

    @MapsId("learnMethodId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "learn_method_id")
    private MoveLearnMethod learnMethod;

    /** Level for "Level up" (0 = learned on evolution); 0 for other methods. */
    public short getLevel() {
        return id.getLevel();
    }

    @Embeddable
    @Getter
    @EqualsAndHashCode
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Id implements Serializable {

        @Column(name = "pokemon_id")
        private Integer pokemonId;

        @Column(name = "game_id")
        private Integer gameId;

        @Column(name = "move_id")
        private Integer moveId;

        @Column(name = "learn_method_id")
        private Integer learnMethodId;

        @Column(name = "level", columnDefinition = "SMALLINT")
        private short level;
    }
}
