"use client";

import { useCallback, useEffect, useRef, useState } from "react";
import { pokemonPageQuery } from "@/lib/pokemon-query";
import type { Page, PokemonFilters, PokemonSummary } from "@/types/pokemon";

type Status = "idle" | "loading" | "error";

/** Loads a page from this app's route handler (/api/pokemon), which calls the backend from the server. */
async function fetchPage(filters: PokemonFilters, page: number, signal: AbortSignal): Promise<Page<PokemonSummary>> {
  const response = await fetch(`/api/pokemon?${pokemonPageQuery(filters, page)}`, { signal });
  if (!response.ok) throw new Error(`GET /api/pokemon returned ${response.status}`);
  return response.json();
}

function sameFilters(a: PokemonFilters, b: PokemonFilters): boolean {
  return a.search === b.search && a.type === b.type && a.generation === b.generation;
}

/**
 * The Pokémon list, one page at a time. Starts from `initialPage` (rendered on the server for
 * no filters). When `filters` change, it starts over from page 0; `loadMore()` appends the next
 * page. Responses for filters that are no longer current are dropped.
 */
export function usePokemonPages(initialPage: Page<PokemonSummary>, filters: PokemonFilters) {
  const [items, setItems] = useState(initialPage.items);
  const [page, setPage] = useState(initialPage.page);
  const [hasNext, setHasNext] = useState(initialPage.hasNext);
  const [totalItems, setTotalItems] = useState(initialPage.totalItems);
  const [status, setStatus] = useState<Status>("idle");
  // True while the list is being replaced because the filters changed (not while appending).
  const [isReplacing, setIsReplacing] = useState(false);

  const filtersRef = useRef(filters);
  const loadedFiltersRef = useRef<PokemonFilters>({});
  const requestRef = useRef<AbortController | null>(null);

  const load = useCallback(async (forFilters: PokemonFilters, pageNumber: number) => {
    requestRef.current?.abort();
    const request = new AbortController();
    requestRef.current = request;
    setStatus("loading");
    try {
      const result = await fetchPage(forFilters, pageNumber, request.signal);
      if (request.signal.aborted || !sameFilters(forFilters, filtersRef.current)) return;
      setItems((current) => (pageNumber === 0 ? result.items : [...current, ...result.items]));
      setPage(result.page);
      setHasNext(result.hasNext);
      setTotalItems(result.totalItems);
      loadedFiltersRef.current = forFilters;
      setStatus("idle");
      setIsReplacing(false);
    } catch (error) {
      if (request.signal.aborted) return;
      console.error(error);
      setStatus("error");
    }
  }, []);

  // New filters: start over from the first page.
  useEffect(() => {
    filtersRef.current = filters;
    if (sameFilters(filters, loadedFiltersRef.current)) return;
    setIsReplacing(true);
    void load(filters, 0);
  }, [filters, load]);

  useEffect(() => () => requestRef.current?.abort(), []);

  /** Append the next page, unless one is already loading or there are no more. */
  const loadMore = useCallback(() => {
    if (status !== "idle" || !hasNext || isReplacing) return;
    void load(filtersRef.current, page + 1);
  }, [status, hasNext, isReplacing, load, page]);

  /** Try the last failed request again. */
  const retry = useCallback(() => {
    void load(filtersRef.current, isReplacing ? 0 : page + 1);
  }, [isReplacing, load, page]);

  return { items, totalItems, hasNext, isLoading: status === "loading", isReplacing, hasError: status === "error", loadMore, retry };
}
