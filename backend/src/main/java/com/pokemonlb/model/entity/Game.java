package com.pokemonlb.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** A main-series game (or paired versions / DLC sharing one Pokédex). */
@Entity
@Table(name = "game")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Game {

    @Id
    private Integer id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false, columnDefinition = "SMALLINT")
    private Short generation;

    @Column(name = "release_order", nullable = false, unique = true, columnDefinition = "SMALLINT")
    private Short releaseOrder;
}
