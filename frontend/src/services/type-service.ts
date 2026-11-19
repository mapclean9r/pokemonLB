import "server-only";

import { apiGet } from "@/services/api-client";
import type { PokemonType } from "@/types/pokemon";

// Calls to the backend's TypeController (/api/v1/types). See backend/docs/api/types.md.

/** The 18 types, sorted by id. */
export function getTypes(): Promise<PokemonType[]> {
  return apiGet<PokemonType[]>("/types");
}
