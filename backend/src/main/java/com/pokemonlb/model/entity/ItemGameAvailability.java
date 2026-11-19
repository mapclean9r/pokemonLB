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

/** Standalone row: an item exists in a game. No relations to other tables. */
@Entity
@Table(name = "item_game_availability")
@IdClass(ItemGameAvailability.Key.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemGameAvailability {

    @Id
    @Column(name = "item_id")
    private Integer itemId;

    @Id
    @Column(name = "game_id")
    private Integer gameId;

    @Column(name = "item_name", nullable = false, length = 100)
    private String itemName;

    @Column(nullable = false, length = 50)
    private String category;

    @Column(name = "game_name", nullable = false, length = 100)
    private String gameName;

    @Column(nullable = false, columnDefinition = "SMALLINT")
    private Short generation;

    public record Key(Integer itemId, Integer gameId) implements Serializable {}
}
