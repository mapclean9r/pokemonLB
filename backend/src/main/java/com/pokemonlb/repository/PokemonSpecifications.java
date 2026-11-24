package com.pokemonlb.repository;

import com.pokemonlb.model.entity.Game;
import com.pokemonlb.model.entity.Pokemon;
import com.pokemonlb.model.entity.PokemonType;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.data.jpa.domain.Specification;

/**
 * Filters for the paged Pokémon list. Every value is bound as a query parameter (Criteria API),
 * never concatenated into the query.
 */
public final class PokemonSpecifications {

    private static final char LIKE_ESCAPE = '\\';

    private PokemonSpecifications() {}

    /**
     * Pokémon matching all filters that are set; null filters are ignored.
     *
     * @param search     exact Pokédex number ("25" or "#25"), or part of the name, ignoring case and accents
     * @param type       type name, primary or secondary, ignoring case (e.g. "fire")
     * @param generation generation the Pokémon was introduced in
     * @param gameId     only Pokémon in this game's Pokédex
     */
    public static Specification<Pokemon> matching(String search, String type, Short generation, Integer gameId) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (search != null && !search.isBlank()) {
                String term = search.trim();
                if (term.matches("#?\\d{1,9}")) {
                    predicates.add(cb.equal(root.get("id"), Integer.parseInt(term.replace("#", ""))));
                } else {
                    // unaccent() comes from the PostgreSQL extension enabled in V8__enable_unaccent.sql.
                    Expression<String> name = cb.function("unaccent", String.class, cb.lower(root.get("name")));
                    predicates.add(cb.like(name, "%" + escapeLike(normalize(term)) + "%", LIKE_ESCAPE));
                }
            }

            if (type != null && !type.isBlank()) {
                Subquery<Integer> withType = query.subquery(Integer.class);
                Root<PokemonType> pokemonType = withType.from(PokemonType.class);
                withType.select(cb.literal(1)).where(
                        cb.equal(pokemonType.get("pokemon"), root),
                        cb.equal(cb.lower(pokemonType.get("type").get("name")), type.trim().toLowerCase(Locale.ROOT)));
                predicates.add(cb.exists(withType));
            }

            if (generation != null) {
                predicates.add(cb.equal(root.get("generation"), generation));
            }

            if (gameId != null) {
                Subquery<Integer> inGame = query.subquery(Integer.class);
                Root<Pokemon> sameSpecies = inGame.from(Pokemon.class);
                Join<Pokemon, Game> games = sameSpecies.join("games");
                inGame.select(cb.literal(1)).where(
                        cb.equal(sameSpecies, root),
                        cb.equal(games.get("id"), gameId));
                predicates.add(cb.exists(inGame));
            }

            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }

    /** Lowercase without accents, matching unaccent(lower(name)) in the database. */
    static String normalize(String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("\\p{M}", "").toLowerCase(Locale.ROOT);
    }

    /** Treat %, _ and the escape character in user input as plain characters. */
    static String escapeLike(String text) {
        return text.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
    }
}
