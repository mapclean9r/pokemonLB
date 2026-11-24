"use client";

import { useMemo, useState } from "react";
import { useDebouncedValue } from "@/hooks/use-debounced-value";
import type { PokemonFilters } from "@/types/pokemon";

/** How long typing has to pause before the search is sent. */
const SEARCH_DELAY_MS = 1000;

/**
 * Search and filter state for the Pokémon list: a text query (name or number), one type and
 * one generation. `filters` is what the list should be loaded with; the query part only
 * updates once typing pauses, so not every keystroke triggers a request.
 */
export function usePokemonFilters() {
  const [query, setQuery] = useState("");
  const [type, setType] = useState<string | null>(null);
  const [generation, setGeneration] = useState<number | null>(null);

  const debouncedQuery = useDebouncedValue(query.trim(), SEARCH_DELAY_MS);

  const filters = useMemo<PokemonFilters>(
    () => ({
      search: debouncedQuery || undefined,
      type: type ?? undefined,
      generation: generation ?? undefined,
    }),
    [debouncedQuery, type, generation],
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

  return { query, setQuery, type, toggleType, generation, toggleGeneration, filters, isFiltered, clearFilters };
}
