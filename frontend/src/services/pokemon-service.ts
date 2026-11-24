import "server-only";

import { pokemonPageQuery, POKEMON_PAGE_SIZE } from "@/lib/pokemon-query";
import { apiGet } from "@/services/api-client";
import type { Page, PokemonFilters, PokemonSummary } from "@/types/pokemon";

// Calls to the backend's PokemonController (/api/v1/pokemon). See backend/docs/api/pokemon.md.

/** One page of Pokémon in Pokédex order, matching the filters. page is zero-based. */
export function getPokemonPage(
  filters: PokemonFilters = {},
  page = 0,
  size = POKEMON_PAGE_SIZE,
): Promise<Page<PokemonSummary>> {
  return apiGet<Page<PokemonSummary>>(`/pokemon?${pokemonPageQuery(filters, page, size)}`);
}
