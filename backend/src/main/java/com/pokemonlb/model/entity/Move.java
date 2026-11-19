package com.pokemonlb.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "move")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Move {

    @Id
    private Integer id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 20)
    private String type;

    /** Physical, Special or Status. */
    @Column(name = "damage_class", nullable = false, length = 10)
    private String damageClass;

    /** Null for status and variable-power moves. */
    @Column(columnDefinition = "SMALLINT")
    private Short power;

    /** Null for moves that never miss. */
    @Column(columnDefinition = "SMALLINT")
    private Short accuracy;

    @Column(columnDefinition = "SMALLINT")
    private Short pp;

    @Column(nullable = false, columnDefinition = "SMALLINT")
    private Short priority;

    /** Generation the move was introduced in. */
    @Column(nullable = false, columnDefinition = "SMALLINT")
    private Short generation;
}
