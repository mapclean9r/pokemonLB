import "server-only";

import { apiGet } from "@/services/api-client";
import type { PokemonSummary } from "@/types/pokemon";

// Calls to the backend's PokemonController (/api/v1/pokemon). See backend/docs/api/pokemon.md.

/** All 1025 Pokémon, sorted by Pokédex number. */
export function getAllPokemon(): Promise<PokemonSummary[]> {
  return apiGet<PokemonSummary[]>("/pokemon");
}
