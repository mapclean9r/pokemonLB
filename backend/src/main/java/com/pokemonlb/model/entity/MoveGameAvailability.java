package com.pokemonlb.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Standalone row: a move exists in a game. No relations to other tables. */
@Entity
@Table(name = "move_game_availability")
@IdClass(MoveGameAvailability.Key.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MoveGameAvailability {

    @Id
    @Column(name = "move_id")
    private Integer moveId;

    @Id
    @Column(name = "game_id")
    private Integer gameId;

    @Column(name = "move_name", nullable = false, length = 50)
    private String moveName;

    @Column(name = "move_type", nullable = false, length = 20)
    private String moveType;

    @Column(name = "game_name", nullable = false, length = 100)
    private String gameName;

    @Column(nullable = false, columnDefinition = "SMALLINT")
    private Short generation;

    public record Key(Integer moveId, Integer gameId) implements Serializable {}
}
