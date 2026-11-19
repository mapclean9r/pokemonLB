package com.pokemonlb.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** How a move is learned: level up, egg, tutor, machine, ... */
@Entity
@Table(name = "move_learn_method")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MoveLearnMethod {

    @Id
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
}
