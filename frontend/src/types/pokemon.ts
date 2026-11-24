// Shapes of the backend API responses (see backend/docs/api/). Safe to import anywhere.

/** One Pokémon in a list — GET /api/v1/pokemon */
export type PokemonSummary = {
  id: number;
  name: string;
  generation: number;
  /** Primary type first, e.g. ["Fire", "Flying"]. */
  types: string[];
  imageUrl: string;
};

/** One page of a longer list. page is zero-based. */
export type Page<T> = {
  items: T[];
  page: number;
  size: number;
  totalItems: number;
  totalPages: number;
  hasNext: boolean;
};

/** Filters for the paged Pokémon list — query parameters of GET /api/v1/pokemon. Omitted = no filter. */
export type PokemonFilters = {
  /** Exact Pokédex number ("25" or "#25"), or part of the name, ignoring case and accents. */
  search?: string;
  /** Type name, primary or secondary, e.g. "Fire". */
  type?: string;
  generation?: number;
};

/** One of the 18 types — GET /api/v1/types */
export type PokemonType = {
  id: number;
  name: string;
  /** Generation the type was introduced in. */
  generation: number;
};
