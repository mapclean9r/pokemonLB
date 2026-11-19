package com.pokemonlb.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** One of the 18 Pokémon types (Normal, Fire, Water, ...). */
@Entity
@Table(name = "type")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Type {

    @Id
    private Integer id;

    @Column(nullable = false, unique = true, length = 20)
    private String name;

    /** Generation the type was introduced in (Steel and Dark: 2, Fairy: 6). */
    @Column(nullable = false, columnDefinition = "SMALLINT")
    private Short generation;
}
