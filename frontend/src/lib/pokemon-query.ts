import type { PokemonFilters } from "@/types/pokemon";

/** Pokémon per page, matching the backend's default. */
export const POKEMON_PAGE_SIZE = 20;

/**
 * Query string for one page of the Pokémon list, e.g. "page=1&size=20&type=Fire".
 * The same parameter names are used by the backend (/api/v1/pokemon) and by this app's
 * route handler (/api/pokemon), so both sides build it here.
 */
export function pokemonPageQuery(filters: PokemonFilters, page: number, size = POKEMON_PAGE_SIZE): string {
  const params = new URLSearchParams({ page: String(page), size: String(size) });
  const search = filters.search?.trim();
  if (search) params.set("search", search);
  if (filters.type) params.set("type", filters.type);
  if (filters.generation !== undefined) params.set("generation", String(filters.generation));
  return params.toString();
}
