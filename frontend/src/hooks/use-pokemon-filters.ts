"use client";

import { useDeferredValue, useMemo, useState } from "react";
import { normalizeText } from "@/lib/format";
import type { PokemonSummary } from "@/types/pokemon";

/** True when the query matches by exact Pokédex number ("25", "#25") or by name (accent-insensitive). */
function matchesQuery(pokemon: PokemonSummary, normalizedQuery: string): boolean {
  if (!normalizedQuery) return true;
  if (/^#?\d+$/.test(normalizedQuery)) {
    return pokemon.id === Number(normalizedQuery.replace("#", ""));
  }
  return normalizeText(pokemon.name).includes(normalizedQuery);
}

/**
 * Search and filter state for a list of Pokémon: a text query (name or number),
 * one type (primary or secondary) and one generation. Filtering runs in the browser.
 */
export function usePokemonFilters(pokemon: PokemonSummary[]) {
  const [query, setQuery] = useState("");
  const [type, setType] = useState<string | null>(null);
  const [generation, setGeneration] = useState<number | null>(null);

  // Typing stays responsive while the list re-filters in the background.
  const deferredQuery = useDeferredValue(normalizeText(query.trim()));

  const results = useMemo(
    () =>
      pokemon.filter(
        (p) =>
          matchesQuery(p, deferredQuery) &&
          (type === null || p.types.includes(type)) &&
          (generation === null || p.generation === generation),
      ),
    [pokemon, deferredQuery, type, generation],
  );

  const isFiltered = query.trim() !== "" || type !== null || generation !== null;

  /** Select a type, or clear it when the same type is selected again. */
  function toggleType(name: string | null) {
    setType((current) => (name === null || current === name ? null : name));
  }

  /** Select a generation, or clear it when the same generation is selected again. */
  function toggleGeneration(value: number | null) {
    setGeneration((current) => (value === null || current === value ? null : value));
  }

  function clearFilters() {
    setQuery("");
    setType(null);
    setGeneration(null);
  }

  return {
    query,
    setQuery,
    type,
    toggleType,
    generation,
    toggleGeneration,
    results,
    total: pokemon.length,
    isFiltered,
    clearFilters,
  };
}
