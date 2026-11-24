package com.pokemonlb.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** A main-series ability, e.g. Static or Overgrow. */
@Entity
@Table(name = "ability")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ability {

    @Id
    private Integer id;

    /** Not unique: "As One" exists once per Calyrex rider form. */
    @Column(nullable = false, length = 50)
    private String name;

    /** Generation the ability was introduced in. */
    @Column(nullable = false, columnDefinition = "SMALLINT")
    private Short generation;

    /** Short description of what the ability does. */
    @Column(columnDefinition = "TEXT")
    private String effect;
}
