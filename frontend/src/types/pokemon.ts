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

/** One of the 18 types — GET /api/v1/types */
export type PokemonType = {
  id: number;
  name: string;
  /** Generation the type was introduced in. */
  generation: number;
};
