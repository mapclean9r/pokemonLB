package com.pokemonlb.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pokemon")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pokemon {

    /** National Pokédex number. */
    @Id
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(nullable = false, columnDefinition = "SMALLINT")
    private Short generation;

    /** Official artwork (PNG). */
    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    /** Games whose Pokédex includes this Pokémon, via the pokemon_game middle table. */
    @ManyToMany
    @JoinTable(
            name = "pokemon_game",
            joinColumns = @JoinColumn(name = "pokemon_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id"))
    private Set<Game> games = new HashSet<>();

    /** Current typing, primary type first, via the pokemon_type middle table. */
    @OneToMany(mappedBy = "pokemon")
    @OrderBy("id.slot")
    private List<PokemonType> types = new ArrayList<>();

    /** Type names in slot order, e.g. ["Fire", "Flying"]. Call inside a transaction. */
    public List<String> getTypeNames() {
        return types.stream().map(pokemonType -> pokemonType.getType().getName()).toList();
    }
}
