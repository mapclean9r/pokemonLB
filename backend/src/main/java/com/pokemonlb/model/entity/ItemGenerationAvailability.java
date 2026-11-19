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

/** Standalone row: an item exists in a generation. No relations to other tables. */
@Entity
@Table(name = "item_generation_availability")
@IdClass(ItemGenerationAvailability.Key.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemGenerationAvailability {

    @Id
    @Column(name = "item_id")
    private Integer itemId;

    @Id
    @Column(columnDefinition = "SMALLINT")
    private Short generation;

    @Column(name = "item_name", nullable = false, length = 100)
    private String itemName;

    @Column(nullable = false, length = 50)
    private String category;

    public record Key(Integer itemId, Short generation) implements Serializable {}
}
